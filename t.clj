; ((:chsk-send! comm) :sente/all-users-without-uid [:evento/datos {:a 1 :b 2}])
(do
  (def ^:macro λ #'fn)
  (require '[clojure.string :as st])
  (require '[clojure.java.jdbc :as j])
  (require '[com.stuartsierra.component :as component])
  (require '[domain.core :refer :all] :reload)
  (require '[domain.horarios :as horarios] :reload)
  (require '[clojure.java.jdbc :as j])

  (use 'server.utils.watch :reload)
  (use 'server.core :reload)

  (def config
  {:mode :dev
   :db {:subname (str "//localhost:3306/ca?"
                      "useUnicode=yes"
                      "&characterEncoding=latin1"
                      "&serverTimezone=UTC")
        :user "root"
        :db (str "ca?"
                 "useUnicode=yes"
                 "&characterEncoding=latin1"
                 "&serverTimezone=UTC")
        :password ""
        :useUnicode "yes"
        :characterEncoding "latin1"}
   :webapp {:port 9001
            :mode :dev
            :base-url "http://localhost:9001"
            :php-base-url "http://localhost/ca/shp/"
            }
   })


  (def system (app config))
  (alter-var-root #'system component/start)
  (def dbc (:db system))
  (def comm (:comm system))


  ; Watchers
  (stop-all-watched)
  (watch "./src/domain/core.clj"
         (fn [ctx e]
           (require '[domain.core :refer :all] :reload)
           (println "Reloaded domain core.")))


  (watch "./src/server/handlers.clj"
         (fn [ctx e]
           (require '[server.handlers :as handlers] :reload)
           (println "Reloaded handlers.")))

  (watch "./src/server/components/communicator.clj"
         (fn [ctx e]
           (require '[server.components.communicator :as comm] :reload)
           (alter-var-root #'system component/stop)
           (alter-var-root #'system component/start)
           (println "Reloaded communicator component.")))

  (watch "./src/server/components/handler.clj"
         (fn [ctx e]
           (require '[server.components.handler :as handler] :reload)
           (alter-var-root #'system component/stop)
           (alter-var-root #'system component/start)
           (println "Reloaded handler component.")))

  (watch "./src/domain/horarios.clj"
         (fn [ctx e]
           (require '[domain.horarios :as horarios] :reload)
           (println "Reloaded horarios domain.")))

  )
; Fin do general


(do
  (alter-var-root #'system component/stop) 
  (alter-var-root #'system component/start))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Database
(j/with-db-transaction
  (exec dbc "insert into carrera (nombre, chorario) values (:nombre, :chorario)" {:nombre "NUEVA3" :chorario ""})
    )
(pprint (q dbc "select * from carrera"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; FUNCTIONAL
(require '[cats.core :as m])
(require '[cats.monad.either :as ei])
(require '[cats.monad.exception :as ex])
(require '[clojure.spec :as s])
(require '[clojure.core.reducers :as r])

(defn eex
  [err exe f v]
  (if (map? err)
    err
    (hash-map :error err :exception exe :fn f :value v)))

(defn efmap
  "
  f function to apply
  v value, left, right or value.
  fo origin function (used for fapply)"
  ([f v fo]
  (if (not (ei/left? v))
    (let [nv (if (ei/right? v) v (ei/right v))
          r (ex/try-on (m/fmap f nv))]
      (if (ex/success? r)
        @r
        (ei/left (eex :exception r fo v))))
    (ei/left  (eex @v nil nil nil))))
  ([f v]
   (efmap f v f)))

(defn hola [x] (str "hola " x))
(s/fdef hola :args (s/cat :x string?) :ret string?)
(s/instrument-all)

(def ident (fn identity2 [x] x))

(efmap inc (ei/right nil))
(efmap inc (ei/right 1))
(efmap inc (ei/left "joder con el error"))
(efmap hola "hola")

(efmap hola (efmap hola (efmap ident "hola")))

(->> "hola" (efmap ident) (efmap hola) (efmap hola))

(->> 234
     (efmap ident)
     (efmap hola)
     (efmap hola))


(pprint 
(map #(efmap identity %)
     (mapv #(efmap hola %)
           [(ei/right "mundo") (ei/left "hola") (ei/right "hola")])) )

(filter ei/either? [(ei/right 1) (ei/left "error")])

(defn eapply
  [f & valus]
  (let [lfs (filter ei/left? valus)]
  (if (> (count lfs) 0)
    (ei/left (eex @(first lfs) nil nil nil))
    (efmap (partial apply f)
           (map #(if (ei/right? %)
                   (deref %)
                   %)
                valus)
           f))))

(eapply inc (ei/left "Problema"))
(eapply + (ei/right 2) (ei/right 3) (ei/right 5))


(reduce (partial eapply +) [(ei/right 1) (ei/right 3) (ei/right 4)])

(reduce (partial eapply +) [(ei/right 1) (ei/right 3) (ei/right 4) (ei/left "error")])

(reduce (partial eapply +) [1 2 3  4])

(reduce (partial eapply +) [1 2 3 (ei/right 5) 4])
(reduce (partial eapply +) [1 2 3 (ei/left "error en parametro") 4])


(efmap inc (ei/right 3))
(efmap inc 20)
(efmap inc nil)

(eapply + 1 2 3 nil 5)
(eapply + 1 2 3 (ei/right 5) 5)

(r/fold (partial eapply +) (mapv ei/right (range 0 10000000000)))


;;;;;;
;(def db {:class "com.mysql.cj.jdbc.Driver"
;         :subprotocol "mysql"
;         :subname "//127.0.0.1:3306/ca?serverTimezone=UTC"
;         :user "root"
;         :pass ""})

;;;;;;;;;;;;;;;;;;;;;

(require '[clojure.spec :as s])
(require '[clojure.spec.gen :as gen])
(require '[clojure.spec.test :as test])
(require '[clojure.string :as string])

(defn foo-gen
  []
  (->> (s/gen (s/int-in 1 100))
       (gen/fmap #(str "FOO-" %))))

(s/def ::id
 (s/spec (s/and string?
                #(string/starts-with? % "FOO-"))
         :gen foo-gen))

;(s/exercise ::id 10 {::id foo-gen} )
(s/exercise ::id )

