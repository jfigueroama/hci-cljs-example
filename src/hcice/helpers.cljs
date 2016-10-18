(ns hcice.helpers
  (:require [reagent-material-ui.core :as mui
             :refer [FontIcon]]
            [clojure.string :as string]
            [cljsjs.material-ui-svg-icons :as svg-icons]
            [reagent.core :as reagent]))


(defn info [& data] (apply (partial (.-info js/console)) data) )

(defn svg-icons
  []
  (sort 
  (map keyword (keys (js->clj js/MaterialUISvgIcons)))))

(defn svg-icon
  "Importa algo desde js/MaterialUI.
  En caso de no encontrarla muestra un avatar de error
  TODO cambiar default para mostrar error!"
  [iname]
  (reagent/adapt-react-class (or (aget js/MaterialUISvgIcons (name iname))
                                 (aget js/MaterialUISvgIcons "ContentAdd"))))

(defonce colors
  (let [cos (js->clj
              (-> js/MaterialUIStyles
                  (aget "colors")))]
    (zipmap (map keyword (keys cos)) (vals cos))))


(defn color
  [k]
  (get colors k))

(defn Icon
  "Crea un FontIcon mezclando bien los atributos mandados.

  [Icon {:className \"otra\" :color \"blue\" :hoverColor \"red\"
         :style {:font-size \"20px\"}} :flight_takeoff]"
  ([n]
   [FontIcon {:className "material-icons"} (name n)])
  ([atts n]
   (let [cn (if (some? (:className atts))
              (string/join " " ["material-icons" (:className atts)])
              "material-icons") ]
   [FontIcon (assoc atts
                    :className cn
                    :color (or (:color atts) "#000000"))
    (name n)])))
 
(defn mui-theme
  [paleta]
  (mui/getMuiTheme
    (clj->js {:palette paleta })))
