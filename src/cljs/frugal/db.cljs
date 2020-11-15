(ns frugal.db)

(def default-db
  {:shopping-list [{:label "Milch" :id "Milch" :bought? false}
                   {:label "Käse" :id "Käse" :bought? false}
                   {:label "Gurke" :id "Gurke" :bought? false}
                   {:label "Waschmittel" :id "Waschmittel" :bought? false}]
   :create-item ""})
