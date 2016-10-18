(ns hcice.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame
             :refer [reg-sub subscribe register-sub dispatch] ]
            [devtools.core :as devtools]
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
            [sch.helpers :as helpers
             :refer [info colors color Icon svg-icon svg-icons]]))

;;; DB
(defonce
  fdb
  {:nombre "Jose"})

;;; Subs
(re-frame/reg-sub
  :nombre
  (fn [db _] (:nombre db)))

;;; Events
(re-frame/reg-event-db
 :inicializar-db
 (fn  [_ _]
   fdb))
(re-frame/reg-event-db :cambiar (fn [_ [_ ndb]] ndb)) 


(re-frame/reg-event-db
 :cambiar-nombre
 (fn  [db [evname evt n]]
   (info evname evt n)
   (assoc db :nombre n)))




;;; Views

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
                  [(svg-icon s) {:style {:width "60px" :height "60px"}} ] (str s) ]))]
        [:br]
        (into [:div]
              (for [c (sort (keys colors))]
                ^{:key (str c)}
                [:div {:style {:float "left" :width "300px"
                               :background-color (c colors)}} (str c)]))
        ] ; tab
        [Tab {:label "Tab2" } "contenido 2" ] ]
        ]
        ]])))





;;; Core

(defn mount-root []
  (reagent/render [hola]
                  (.getElementById js/document "app")))

(defn ^:export init []
      (do
        (devtools/install!)
        (re-frame/dispatch-sync [:inicializar-db])
        (mount-root)))
