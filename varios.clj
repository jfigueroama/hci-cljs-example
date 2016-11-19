
; Programación Funcional básica con Clojure

;; Una función es un "morfismo".
;; El resultado de una función pura depende de
;; las entradas. No se toma nada mas en cuenta.
(defn abs
  [x]
  (if (< x 0)
    (* -1 x)
    x))

(abs -20)
(abs 20)

(defn dihola
  "Funcion impura."
  [nombre]
  (println "Hola " nombre))

(dihola "jose")
;;; Inmutables!!

(def a 1)
a


(take 20 (iterate inc 0))

(defn adder
  [x]
  (fn [y]
    (+ y x)))

((adder 2) 5)

(def add2 (adder 2))

(add2 5)
(add2 10)


(defn avg
  [nums]
  {:pre [(coll? nums) (> (count nums) 0)]}
  (/ (reduce + nums) (count nums)))

(avg 10)
(avg [])
(avg [10])
(avg [10 20])


"hola"


