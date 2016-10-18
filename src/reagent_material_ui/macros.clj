; shamelessly stolen from https://github.com/tuhlmann/reagent-material

(ns reagent-material-ui.macros)

(def material-tags
  '[
    AppBar
    AutoComplete
    Avatar
    Badge
    BottomNavigation
    BottomNavigationItem
    Card
    CardActions
    CardHeader
    CardMedia
    CardText
    CardTitle
    Checkbox
    Chip
    CircularProgress
    DatePicker
    Dialog
    Divider
    Drawer
    DropDownMenu
    FlatButton
    FloatingActionButton
    FontIcon
    GridList
    GridTile
    IconButton
    IconMenu
    LinearProgress
    List
    ListItem
    Menu
    MenuItem
    Paper
    Popover
    RadioButton
    RadioButtonGroup
    RaisedButton
    RefreshIndicator
    SelectField
    Slider
    Snackbar
    Step
    StepButton
    StepContent
    StepLabel
    Stepper
    Subheader
    SvgIcon
    Tab
    Tabs
    Table
    TableBody
    TableFooter
    TableHeader
    TableHeaderColumn
    TableRow
    TableRowColumn
    TimePicker
    TextField
    Toggle
    Toolbar
    ToolbarGroup
    ToolbarSeparator
    ToolbarTitle
    ])

(defn material-ui-react-import [tname]
  `(def ~tname
     (reagent.core/adapt-react-class (aget js/MaterialUI ~(name tname)))))

(defmacro export-material-ui-react-classes []
  `(do
     ~@(map material-ui-react-import material-tags)
     ))
