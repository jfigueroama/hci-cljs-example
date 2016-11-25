(defproject hci-cljs-example "0.1.0"
  :description "Clojurescript/re-frame/material-ui project to be used in an HCI course."
  :min-lein-version "2.5.3"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [devcards "0.2.2"]
                 [cljsjs/react "15.3.1-0"]
                 [cljsjs/react-dom "15.3.1-0"]
                 [reagent "0.6.0" 
                                    :exclusions
                                     [org.clojure/tools.reader
                                      cljsjs/react]]
                 [re-frame "0.8.0"  :exclusions [cljsjs/react]]
                 [cljsjs/material-ui "0.16.0-0"  :exclusions [cljsjs/react]]
                 [lein-light-nrepl "0.3.3"]
                 [binaryage/devtools "0.8.2"]
                 ]

  ;:main server.core

  :plugins [[lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]] ]

  :source-paths ["src"] 
  :clean-targets ^{:protect false} ["resources/public/js/hcice/out" "target"]
  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   [figwheel-sidecar "0.5.8"]]
    :source-paths ["src"]
    :repl-options {; for nREPL dev you really need to limit output
                   :init (set! *print-length* 50)
                   :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
                   ;:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
                   }}}

  :cljsbuild
  {:builds
   [{:id           "devcards"
     :source-paths ["src"]
     :figwheel     {;:on-jsload "hcice.core/mount-root"
                    :devcards true  ;; <- note this
                    :load-warninged-code true
                    }
     :compiler     {:devcards true
                    :main                 hcice.core
                    :output-to            "resources/public/js/hcice/devcards_app.js"
                    :output-dir           "resources/public/js/hcice/devcards_out"
                    :asset-path           "js/hcice/devcards_out"
                    :source-map-timestamp true}}

    {:id           "dev"
     :source-paths ["src"]
     :figwheel     {;:on-jsload "hcice.core/mount-root"
                    :load-warninged-code false
                    }
     :compiler     {:main                 hcice.core
                    :output-to            "resources/public/js/hcice/app.js"
                    :output-dir           "resources/public/js/hcice/out"

                    :asset-path           "/js/hcice/out"
                    :source-map-timestamp true}}

    {:id           "prod"
     :source-paths ["src"]
     :compiler     {:main            hcice.core
                    :output-to       "resources/public/js/hcice/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ]})
