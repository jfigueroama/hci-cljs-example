(ns hcice.subs
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame
             :refer [reg-sub ]]))

(reg-sub :seccion (fn [db _] (:seccion db)))

