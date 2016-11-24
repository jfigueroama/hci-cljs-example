(ns hcice.subs
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame
             :refer [reg-sub ]]))

(reg-sub :db (fn [db _] db))
;(reg-sub :seccion (fn [db _] (:seccion db)))
;(reg-sub :drawer (fn [db _] (:drawer db)))

