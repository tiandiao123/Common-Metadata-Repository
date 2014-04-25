(ns cmr.indexer.services.collection
  "Contains functions to parse and convert collection concept"
  (:require [clojure.string :as s]
            [clj-time.format :as f]
            [cmr.indexer.services.index-service :as idx]
            [cmr.umm.echo10.collection :as collection]
            [cmr.indexer.services.temporal :as temporal]))

(defmethod idx/parse-concept :collection
  [concept]
  (collection/parse-collection (:metadata concept)))

(defmethod idx/concept->elastic-doc :collection
  [concept umm-concept]
  (let [{:keys [concept-id provider-id]} concept
        {{:keys [short-name version-id]} :product
         entry-title :entry-title
         temporal-coverage :temporal-coverage} umm-concept
         ;; each project short name in project-short-names string will be analyzed and indexed
         project-short-names (map :short-name (:projects umm-concept)))
         start-date (temporal/start-date :collection temporal-coverage)
         end-date (temporal/end-date :collection temporal-coverage)]
    {:concept-id concept-id
     :entry-title entry-title
     :entry-title.lowercase (s/lower-case entry-title)
     :provider-id provider-id
     :provider-id.lowercase (s/lower-case provider-id)
     :short-name short-name
     :short-name.lowercase (s/lower-case short-name)
     :version-id version-id
     :version-id.lowercase (s/lower-case version-id)
     :project-sn project-short-names
     :project-sn.lowercase  (map s/lower-case project-short-names)
     :start-date (when start-date (f/unparse (f/formatters :date-time) start-date))
     :end-date (when end-date (f/unparse (f/formatters :date-time) end-date))}))
