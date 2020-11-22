(ns frugal.views
  (:require
   [re-frame.core :as rf]
   [frugal.subs :as subs]
   [reagent.core :as r]
   [clojure.string :as str]
   ))

(defn editable-list []
  (let [shopping-list (rf/subscribe [::subs/shopping-list])]
    [:div.container
      [:ul (for [item (sort-by #(not (:bought? %)) @shopping-list)]
             [:li {:key (:id item)}
              [:div.box
                   [:span {:class (if (:bought? item) "bought")} (:label item)]
                   [:span [:input.checkbox {:type "checkbox"
                                   :on-change #(rf/dispatch [:bought (:id item)])
                                   :checked (:bought? item)} ]]]])]]))

(defn new-item
  []
  (let [value (rf/subscribe [::subs/create-item])]
    (fn []
      [:div.columns [:div.column.is-four-fifths
       [:input.input
         {:type "text"
          :value @value
          :on-change #(rf/dispatch [:create-item-changed (-> % .-target .-value)])}]]
      [:div.column [:button.button {:on-click #(rf/dispatch [:add-item @value])} "Add Item"]]])))

(defn suggestions
  []
  (let [create-item @(rf/subscribe [::subs/create-item])
        suggestions @(rf/subscribe [::subs/suggestions create-item])]
    (if (not (str/blank? create-item))
      [:div (for [item suggestions] [:li {:key (:id item)}
                                     [:button.button
                                      {:on-click #(rf/dispatch
                                                   [:reset-item (:id item)])}
                                      (:label item)]])])))

(defn last-bought-tracker []
  (let [last-bought @(rf/subscribe [::subs/last-bought])]
    (.log js/console "tracker" last-bought)
    (if (not (nil? last-bought))
      [:div.last-bought (:label last-bought)])))

(defn item-creator []
  [:div.container
   [new-item]
   [:div.columns
    [:div [suggestions]]]])

(defn edit-screen []
  [:div [last-bought-tracker] [editable-list] [item-creator]])

(defn cart-screen []
  [:button.button {:on-click #(rf/dispatch [:to-edit-mode])} "Edit Mode"])

(defn main-panel
  []
  (let [edit-mode? @(rf/subscribe [::subs/edit-mode?])]
    [:div (if edit-mode? [edit-screen] [cart-screen])]))
