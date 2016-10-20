(ns hcice.events
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame
             :refer [dispatch] ]
            [hcice.helpers :as helpers :refer [info]]
            [hcice.db :refer [db]]))

(re-frame/reg-event-db
 :inicializar-db
 (fn  [_ _]
   db))

(re-frame/reg-event-db
  :cambiar
  (fn [_ [_ ndb]] ndb)) 

(re-frame/reg-event-db
  :assoc-in
  (fn [db [_ path valor]]
    (assoc-in db path valor)))


(re-frame/reg-event-db
 :seleccionar-seccion
 (fn  [db [_ kseccion]]
   (assoc db :seccion kseccion)))


