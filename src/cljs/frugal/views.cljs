(ns frugal.views
  (:require
   [re-frame.core :as rf]
   [frugal.subs :as subs]
   [reagent.core :as r]
   [clojure.string :as str]
   ))

(defn shopping-list []
  (let [shopping-list (rf/subscribe [::subs/shopping-list])]
    [:ul (for [item (sort-by :bought? @shopping-list)]
           [:li {:key (:id item)}
            [:div
                 [:span {:class (if (:bought? item) "bought")} (:label item)]
                 [:span [:input {:type "checkbox"
                                 :on-change #(rf/dispatch [:bought (:id item)])
                                 :checked (:bought? item)} ]]]])]))

(defn new-item
  []
  (let [value (rf/subscribe [::subs/create-item])]
    (fn []
      [:div
       [:span [:input {:type "text" :value @value :on-change #(rf/dispatch [:create-item-changed (-> % .-target .-value)])}]]
       [:span [:button {:on-click #(rf/dispatch [:add-item @value])} "+"]]])))

(defn suggestions
  []
  (let [create-item @(rf/subscribe [::subs/create-item])
        suggestions @(rf/subscribe [::subs/suggestions create-item])]
    (if (not (str/blank? create-item))
      [:div (for [item suggestions] [:li {:key (:id item)}
                                     [:button {:on-click #(rf/dispatch [:reset-item (:id item)])} (:label item)]])])))

(defn main-panel
  []
  [:div [new-item] [suggestions] [shopping-list]])
