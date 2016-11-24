(ns hcice.views
  (:require [reagent.core :as reagent :refer [as-element]]
            [re-frame.core :as re-frame :refer [subscribe dispatch] ]
            [reagent-material-ui.core :as mui
             :refer [MuiThemeProvider AppBar AutoComplete Avatar
                     Badge BottomNavigation
                     BottomNavigationItem Card CardActions CardHeader
                     CardMedia CardText 
                     CardTitle Checkbox Chip CircularProgress
                     DatePicker Dialog Divider
                     Drawer DropDownMenu FlatButton FloatingActionButton
                     GridList GridTile IconButton IconMenu LinearProgress
                     List ListItem Menu MenuItem Paper Popover RadioButton
                     RadioButtonGroup RaisedButton RefreshIndicator
                     SelectField Slider Snackbar Step StepButton
                     StepContent StepLabel Stepper Subheader SvgIcon Tab
                     Tabs Table TableBody TableFooter TableHeader
                     TableHeaderColumn TableRow TableRowColumn TimePicker
                     TextField Toggle Toolbar
                     ToolbarGroup ToolbarSeparator ToolbarTitle ]]
            [hcice.helpers :as helpers
             :refer [info colors color Icon svg-icon svg-icons
                     mui-theme material-class]]))

#_(defn enombre
  []
  (let [db (subscribe [:db])]
    (fn []
      [:span
       [:span {:style {:color "green"}} (:nombre @db)]
       [:br]
       [:input#inombre {:value (:nombre @db)
                        :on-change
                        (fn click-hn [evt]
                          (dispatch
                            [:assoc-in
                             [:nombre]
                             (-> evt (.-target) (.-value)) ]))}]])))

#_(defn vista-principal
  []
  [:div
   [:span "Contenido nuevo"]
   [:div {:style {
                  :background-color "cyan"
                  :width "200px"
                  :border "1px solid red"}} 
    [:strong "Hola a todos"]
    [:br]
    [:textarea "Contenido inicial"]]
   [:div {:style {
                  :background-color "lightgray"}}
    "Ahora si, va el contenido interesante "
    [enombre] [:br]
    [MuiThemeProvider
     {:muiTheme (mui-theme {:textColor (:indigoA400 colors)
                            :primary1Color "green"})}
      [FlatButton {:label "Hola"
                     :rippleColor "blue"
                     :style {:text-decoration "line-through"}
                     :on-click
                     #(dispatch [:assoc-in [:nombre] "Otro nombre"])}]]]])
       

#_(defn vista-principal
  []
  (let [seccion (subscribe [:seccion])
        drawer (subscribe [:drawer])]
    (fn vista-principal-fn []
      [:div.principal
       [:div.cabeza
        [:p "Ejemplificacion de material-ui con clojurescript para curso de HCI"]
        ]
       [:div.cuerpo
        [:div.barra
         #_{:style {:display "none"}}
         {:on-click #(dispatch [:assoc-in [:drawer] true ])}
         [MuiThemeProvider
          {:muiTheme (mui-theme {:textColor (:indigoA400 colors) :primary1Color "green"})}
          [Drawer {:className "barra" :open @drawer
                   :on-mouse-over #(dispatch [:assoc-in [:drawer] false])}

          [List
           [ListItem {:primaryText "Introduccion"
                       :on-click #(info :intro)}]
           [ListItem {:primaryText "Componentes"
                      :initiallyOpen true
                      :primaryTogglesNestedList true
                      :nestedItems
                      (into []
                            (for [x (range 1 10)]
                              ^{:key x}
                              (as-element
                                [ListItem
                                 {:key x
                                  :on-click
                                  #(dispatch [:assoc-in [:drawer] false])
                                  :primaryText (str "sub" x)}]))) }] ]]]]
        [:div.contenido
         "Contenido extra" ]
        ]])))


(defn hola
  []
  (let [db (subscribe [:db])]
    (fn hola-view []
      [:div "Hola " (:nombre @db)
       ;[:svg {:style {:position "absolute" :top "0px" :left "0px"}}
       ; [:rect {:width 100 :height 200 :fill "#00FF00"} ]
       ; ]
       ;[:svg {:style {:position "absolute" :top "0px" :left "0px"}}
       ; [:rect {:width 50 :height 100 :fill "#00FFFF"}]
       ; ]  
       ; Fin svg
       ;[:br] 

       [MuiThemeProvider
        {:muiTheme
         (mui/getMuiTheme (clj->js {:palette {:textColor "#12f1f0"
                                              :primary1Color  "#0000ff"
                                              }})) }
        ;(mui/getMuiTheme mui/darkBaseTheme)}
        [:div
         (comment [FlatButton {:label "Hola"
                               :rippleColor "green"
                               :style {;:color "red"
                                       ;:background-color "yellow"
                                       :text-decoration "line-through"
                                       }
                               :on-click #(dispatch [:cambiar-nombre % "lluvia"])}])
         [Tabs
          [Tab {:label "Tab 1"} "Contenido t1"
           [Icon {:className "perro"
                  :color "#00FF00"
                  :on-click #(info "hola desde icono")
                  :hoverColor "#FF0000"
                  :style {:font-size "40px"}
                  } :flight_takeoff]
           [FloatingActionButton {:mini true
                                  :href "holaref"
                                  } [Icon :flight_takeoff]]
           [IconButton {:tooltip "Hola despegue!"
                        :href "Otra cosa"
                        }
            [Icon {:color "#00FF00"
                   :hoverColor "#FF0000"
                   } :flight_takeoff]
            ]
           [:br]
           [Badge {:badgeContent 4}
            [Icon {:color "#00FF00"
                   :hoverColor "#FF0000"
                   } :flight_takeoff]]
           [Badge {:badgeContent 10 :secondary true :style {:color "white"}}
            [IconButton {:tooltip "Son 10"
                         :href "algo/10"}
             [Icon {:color "#00FF00"
                    :hoverColor "#FF0000"
                    } :flight_takeoff]
             ]]
           [SvgIcon {:color (:red500 colors) :hoverColor (:blue500 colors)}
            [:path {:d "M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"}] ]

           [(svg-icon :ContentAdd) {:color (:red500 colors) :hoverColor (:blue500 colors)}]
           [FloatingActionButton {:mini true :secondary true :href "nueva"}
            [(svg-icon :ContentAdd {:color "green"})]]
           [FloatingActionButton {:mini true :secondary false :href "nueva2"}
            [(svg-icon :FileFolder )]]
           [(svg-icon :NoEncontrada) {:color "blue"}]
           [:br] [:br]

           [MuiThemeProvider
            {:muiTheme (helpers/mui-theme {:textColor (:indigoA400 colors)
                                           :primary1Color "green"})}
            (into [:div]
                  (for [s (svg-icons)]
                    ^{:key (str s "-icon")}
                    [:div {:style {:float "left" :display "inline" :margin "6px"
                                   :width "300px" :max-width "300px" :overflow "auto"}}
                     [(svg-icon s) {:style {:width "60px" :height "60px"}} ] (str s) ]))] ] ; tab
          [Tab {:label "Tab2" } "contenido 2" ]
          [Tab {:label "Colores"}
           (into [:div {:style {:clear "both"}}]
                 (for [c (sort (keys colors))]
                   ^{:key (str c)}
                   [:div {:style {:width "300px"
                                  :background-color (c colors)}} (str c)]))]
          ]
         ]]  ])))


(defn hola2
  []
  (fn []
    (let [db (subscribe [:db])]
      [:span "Hola " (:nombre @db)])))

(defn vista-principal
  []
  [hola] )
