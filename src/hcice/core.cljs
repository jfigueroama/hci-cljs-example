(ns hcice.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame
             :refer [reg-sub subscribe register-sub dispatch]]
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
                     ToolbarGroup ToolbarSeparator ToolbarTitle]]
            [hcice.helpers :as helpers
             :refer [info colors color Icon svg-icon svg-icons]]
            [hcice.events] [hcice.db] [hcice.subs] [hcice.devcards]
            [hcice.views :as views :refer [vista-principal]]))

(enable-console-print!)

(defn mount-root []
  (reagent/render [vista-principal]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (if-let [node (.getElementById js/document "app")]
    (do
      (devtools/install!)
      (re-frame/dispatch-sync [:inicializar-db])
      (mount-root))))

(init)
