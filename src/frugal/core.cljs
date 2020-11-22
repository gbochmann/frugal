(ns ^:figwheel-hooks frugal.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as re-frame]
            [frugal.events :as events]
            [frugal.views :as views]
            [frugal.config :as config]))

(defn app []
  [:h1 "Works!"])

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn mount []
  (re-frame/clear-subscription-cache!)
  (let [root-el (js/document.getElementById "root")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn ^:after-load re-render []
  (mount))

(defonce start-up (do
                    (re-frame/dispatch-sync [::events/initialize-db])
                    (dev-setup)
                    (mount)
                    true))
