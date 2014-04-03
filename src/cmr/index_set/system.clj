(ns cmr.index-set.system
  "Defines functions for creating, starting, and stopping the application. Applications are
  represented as a map of components. Design based on
  http://stuartsierra.com/2013/09/15/lifecycle-composition and related posts."
  (:require [cmr.common.lifecycle :as lifecycle]
            [cmr.common.log :as log :refer (debug info warn error)]
            [cmr.index-set.api.routes :as routes]
            [cmr.common.api.web-server :as web]
            [cmr.system-trace.context :as context]
            [cmr.index-set.config.elasticsearch-config :as es-config]
            [cmr.index-set.data.elasticsearch :as es]))

(def DEFAULT_PORT 3005)

(def
  ^{:doc "Defines the order to start the components."
    :private true}
  component-order [:log :db :web])

(defn create-system
  "Returns a new instance of the whole application."
  []
  {:log (log/create-logger)
   :db (es/create-elasticsearch-store (es-config/config))
   :web (web/create-web-server DEFAULT_PORT routes/make-api)
   :zipkin (context/zipkin-config "index-set" false)})

(defn start
  "Performs side effects to initialize the system, acquire resources,
  and start it running. Returns an updated instance of the system."
  [this]
  (info "index-set System starting")
  (let [started-system (reduce (fn [system component-name]
                                 (update-in system [component-name]
                                            #(lifecycle/start % system)))
                               this
                               component-order)]
    (info "index-set System started")
    started-system))


(defn stop
  "Performs side effects to shut down the system and release its
  resources. Returns an updated instance of the system."
  [this]
  (info "index-set System shutting down")
  (let [stopped-system (reduce (fn [system component-name]
                                 (update-in system [component-name]
                                            #(lifecycle/stop % system)))
                               this
                               (reverse component-order))]
    (info "index-set System stopped")
    stopped-system))
