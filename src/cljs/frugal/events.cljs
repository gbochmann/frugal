(ns frugal.events
  (:require
   [re-frame.core :as rf]
   [frugal.db :as db]
   ))

 (defn bought [db [_ id]]
   (let [new-list (reduce
                   (fn [result item]
                     (conj
                      result
                      (if (not (= id (:id item)))
                        item
                        (assoc item :bought? (not (:bought? item))))))
                   []
                   (:shopping-list db))]
     (assoc db :shopping-list new-list)))

(defn add-item
  [{:keys [shopping-list] :as db} [_ label]]
  (assoc db
         :shopping-list (conj shopping-list {:label label :id label :bought? false})
         :create-item ""))

(defn reset-item
  [{:keys [shopping-list] :as db} [_ id]]
  (assoc db
         :shopping-list
         (reduce
          (fn [result item] (conj result (if (not (= id (:id item)))
                                           item
                                           (assoc item :bought? false))))
          []
          shopping-list)))

(defn create-item-changed
  [db [_ value]]
  (assoc db :create-item value))


(rf/reg-event-db ::initialize-db (fn [_ _] db/default-db))

(rf/reg-event-db :bought bought)
(rf/reg-event-db :add-item add-item)
(rf/reg-event-db :create-item-changed create-item-changed)
(rf/reg-event-db :reset-item reset-item)
