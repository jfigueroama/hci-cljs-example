(ns hcice.views
  (:require [reagent.core :as reagent :refer [as-element]]
            [re-frame.core :as re-frame :refer [subscribe dispatch] ]
            [reagent-material-ui.core :as mui
             :refer [MuiThemeProvider AppBar AutoComplete Avatar Badge BottomNavigation
                     BottomNavigationItem Card CardActions CardHeader CardMedia CardText 
                     CardTitle Checkbox Chip CircularProgress DatePicker Dialog Divider
                     Drawer DropDownMenu FlatButton FloatingActionButton GridList
                     GridTile IconButton IconMenu LinearProgress List ListItem
                     Menu MenuItem Paper Popover RadioButton RadioButtonGroup
                     RaisedButton RefreshIndicator SelectField Slider Snackbar Step
                     StepButton StepContent StepLabel Stepper Subheader SvgIcon Tab
                     Tabs Table TableBody TableFooter TableHeader TableHeaderColumn
                     TableRow TableRowColumn TimePicker TextField Toggle Toolbar
                     ToolbarGroup ToolbarSeparator ToolbarTitle ]]
            [hcice.helpers :as helpers
             :refer [info colors color Icon svg-icon svg-icons mui-theme material-class]]))


(defn vista-principal
  []
  (let [seccion (subscribe [:seccion])]
    (fn []
      [:div.principal
       [:div.cabeza
        [:p "Ejemplificacion de material-ui con clojurescript para curso de HCI"]
        ]
       [:div.cuerpo
        [:div.barra
         [MuiThemeProvider
          {:muiTheme (mui-theme {:textColor (:indigoA400 colors) :primary1Color "green"})}
          [List
           [ListItem {:primaryText "Introduccion"
                       :on-click #(info :intro)}]
           [ListItem {:primaryText "Componentes"
                      :initiallyOpen true
                      :primaryTogglesNestedList true
                      :nestedItems
                      ;[(as-element [ListItem {:key 1 :primaryText "sub1"}])
                      ; (as-element [ListItem {:key 2 :primaryText "sub2"}])
                      ; (as-element [ListItem {:key 3 :primaryText "sub3"}])
                      ;(as-element [ListItem {:key 4 :primaryText "sub4"}])
                      (into [] (for [x (range 1 10)]
                                 ^{:key x} (as-element [ListItem {:key x :primaryText (str "sub" x)}])))
                      }] ]]]
        [:div.contenido
         "Contenido"
         ]
        ]])))



(defn hola
  []
  (let [nombre (subscribe [:nombre])]
    (fn []
      [:div "Hola " @nombre
       ; Inicia svg
       (comment 
       [:svg {:style {:position "absolute" :top "0px" :left "0px"}}
        [:rect {:width 100 :height 200 :fill "#00FF00"} ]
        ]
       [:svg {:style {:position "absolute" :top "0px" :left "0px"}}
        [:rect {:width 50 :height 100 :fill "#00FFFF"}]
        ] 
       ; Fin svg
       [:br]
        )
       [MuiThemeProvider
        {:muiTheme
         (mui/getMuiTheme (clj->js {:palette {:textColor "#12f1f0"
                          :primary1Color  "#0000ff"
                          }})) }
                    ;(mui/getMuiTheme mui/darkBaseTheme)}
        [:div
        [FlatButton {:label "Hola"
                     :rippleColor "green"
                     :style {;:color "red"
                             ;:background-color "yellow"
                             :text-decoration "line-through"
                            }
                     :on-click #(dispatch [:cambiar-nombre % "lluvia"])}]
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
        ]] ])))



