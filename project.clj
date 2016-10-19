(defproject hci-cljs-example "0.1.0"
  :description "Clojurescript/re-frame/material-ui project to be used in an HCI course."
  :dependencies [[org.clojure/clojure "1.9.0-alpha13"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0" :exclusions
                                     [org.clojure/tools.reader
                                      cljsjs/react]]
                 [binaryage/devtools "0.8.2"]
                 ;[devcards "0.2.2"]
                 [figwheel-sidecar "0.5.8" :scope "test"]
                 [re-frame "0.8.0" ]; :exclusions [cljsjs/react]]
                 [cljsjs/material-ui "0.16.0-0"] 
                 [lein-light-nrepl "0.3.3"]
                 ;[bidi "2.0.12"]
                 ]

  ;:main server.core

  :plugins [[lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]] ]

  :min-lein-version "2.5.3"
  :source-paths ["src"] 
  :clean-targets ^{:protect false} ["resources/public/js/hcice/out" "target"]
  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.8"]]
    }}

  :cljsbuild
  {:builds
   [
    {:id           "dev"
     :source-paths ["src/hcice"]
     :figwheel     {:on-jsload "hcice.core/mount-root"
                    :load-warninged-code true
                    }
     :compiler     {:main                 hcice.core
                    :output-to            "resources/public/js/hcice/app.js"
                    :output-dir           "resources/public/js/hcice/out"

                    :asset-path           "/js/hcice/out"
                    :source-map-timestamp true}}

    {:id           "prod"
     :source-paths ["src/hcice"]
     :compiler     {:main            hcice.core
                    :output-to       "resources/public/js/hcice/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ]}


  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
  )
