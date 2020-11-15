(ns frugal.subs
  (:require
   [re-frame.core :as rf]
   [clojure.string :as str]))

(rf/reg-sub
 ::shopping-list
 (fn [db]
   (:shopping-list db)))

(rf/reg-sub
 ::create-item
 (fn [db]
   (:create-item db)))

(rf/reg-sub
 ::suggestions
 (fn
   [db [_ create-item]]
   (filter (fn [item] (and
                       (:bought? item)
                       (str/includes?
                        (str/lower-case (:label item)) (str/lower-case create-item))))
          (:shopping-list db))))
