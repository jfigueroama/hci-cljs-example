(ns hcice.devcards
  (:require [reagent.core :as reagent]
            [devcards.core :as dc]
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
                     mui-theme material-class]])
 (:require-macros
   [devcards.core :as dc :refer [defcard deftest defcard-rg]] ))

(defcard {:a 1})

(defcard-rg objeto-reagent
  "documentacion"
  (fn [state _]
    [:div [:strong "Hola desde reagent: "] [:br]
          [:i {:on-click #(swap! state update :contador inc)}
           "Contador (click aqui para aumentar):" (:contador @state) ]
          [MuiThemeProvider
           {:muiTheme (mui-theme {:textColor (:indigoA400 colors)
                                  :primary1Color "green"})}
           [FlatButton {:label "Resetear a 0"
                        :rippleColor "blue"
                        :style {}
                        :on-click #(reset! state (assoc @state :contador 0))}]] ])
  (reagent/atom {:contador 1})
  {:inspect-data true
   :history true})
