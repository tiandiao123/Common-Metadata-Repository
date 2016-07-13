(ns cmr.indexer.data.concepts.humanizer
  "Implements transforms to 'humanize' faceted fields on UMM collections.
   See https://wiki.earthdata.nasa.gov/display/CMR/Humanizing+Facets+Design"
  (:require [clojure.string :as str]
            [cmr.common.util :as util]
            [clojure.java.io :as io]
            [cheshire.core :as json]))

(def humanizer-cache
  "Cache of humanizers in the system. Currently this is just a static resource file. In the
  future, it'll come from a new concept and change periodically, so it will need to move
  into the system.
  '(remove string? ...)' allows us to put comments as string elements in the humanizer list file."
  (remove string? (json/decode (slurp (io/resource "humanizers.json")) true)))

(defn- transform-in-all
  "(Convenience method) Similar to update-in-all but calls fn with the parent of the
   value at path and the final key of path."
  [obj path f & args]
  (apply util/update-in-all obj (pop path) f (peek path) args))

(def non-prioritized-fields
  "A list of fields which cannot be prioritized"
  ["science_keyword"])

(def humanizer-field->umm-path
  "Map of humanizer JSON field names to lists of paths into parsed UMM collections
  corresponding to those fields."
  {"platform" [[:platforms :short-name]]
   "instrument" [[:platforms :instruments :short-name]]
   "science_keyword" [
                     [:science-keywords :category]
                     [:science-keywords :topic]
                     [:science-keywords :term]
                     [:science-keywords :variable-level-1]
                     [:science-keywords :variable-level-2]
                     [:science-keywords :variable-level-3]
                     [:science-keywords :detailed-variable]
                     ]
   "project" [[:projects :short-name]]
   "processing_level" [[:product :processing-level-id]]
   "organization" [[:organizations :org-name]]})

(defmulti to-human
  "Map of humanizer JSON type values to functions which take a field value android
  humanizer configuration and return a transformed field value. The functions
  can assume that the humanizer should be applied to the value."
  (fn [humanizer value]
    (:type humanizer)))

(defmethod to-human "trim_whitespace"
  [humanizer value]
  (assoc value
         :value (str/trim (str/replace (:value value) #"\s+" " "))))

(defmethod to-human "capitalize"
  [humanizer value]
  (assoc value
         :value (->> (str/split (:value value) #"\b")
                     (map str/capitalize)
                     str/join)))

(defmethod to-human "alias"
  [humanizer value]
  (assoc value
         :value (:replacement_value humanizer)))

(defmethod to-human "ignore"
  [humanizer value]
  nil)

(defmethod to-human "priority"
  [humanizer value]
  (assoc value
         :priority (:priority humanizer)))

(defn humanizer-key
  "Prefixes a key with the humanizer namespace"
  [key]
  (keyword "cmr.humanized" (name key)))

(defn- humanizer-matches?
  "Tests whether the given humanizer config applies to parent[key]"
  [parent key humanizer]
  (let [match-value (:source_value humanizer)
        value (get-in parent [(humanizer-key key) :value])]
    (and (some? value)
         (or (nil? match-value)
             (= match-value value)))))

(defn- assoc-humanized
  "Returns parent with a humanized version of the field at source-key"
  [parent source-key humanizer]
  (if (humanizer-matches? parent source-key humanizer)
    (let [target-key (humanizer-key source-key)
          value-at-key (get parent target-key)]
      (assoc parent target-key (to-human humanizer value-at-key)))
    parent))

(defn- apply-humanizer
  "Applies the humanizer to the collection"
  [collection humanizer]
  (let [paths (humanizer-field->umm-path (:field humanizer))]
    (reduce #(transform-in-all %1 %2 assoc-humanized humanizer) collection paths)))

(defn- add-humanizer-field
  "(Helper for add-humanizer-fields) Given a parent object and a key
  copies of parent[key] to a key with the humanizer namespace"
  [parent key]
  (assoc parent (humanizer-key key) {:value (get parent key) :priority 0}))

(defn- add-humanizer-fields
  "Duplicates all fields of the collection which could be humanized into keys
  with the humanizer namespace. This allows us to run the humanizers while keeping
  the original fields in place."
  [collection]
  (let [field-paths (apply concat (vals humanizer-field->umm-path))]
    (reduce #(transform-in-all %1 %2 add-humanizer-field) collection field-paths)))

(defn- simplify-humanizer-field
  "Assocs the :value inside of the given key to the parent at key"
  [parent key]
  (let [humanized-key (humanizer-key key)]
    (assoc parent humanized-key (:value (get parent humanized-key)))))

(defn- simplify-humanizer-fields
  "Removes priority fields where appropriate, using the bare string value for non-prioritized fields."
  [collection]
  (let [non-prioritized-paths (select-keys humanizer-field->umm-path non-prioritized-fields)
        field-paths (apply concat (vals non-prioritized-paths))]
    (reduce #(transform-in-all %1 %2 simplify-humanizer-field) collection field-paths)))

(defn umm-collection->umm-collection+humanizers
  "Applies humanizers to a parsed UMM collection"
  ([collection]
   (umm-collection->umm-collection+humanizers collection humanizer-cache))

  ([collection humanizers]
   (simplify-humanizer-fields
    (reduce apply-humanizer (add-humanizer-fields collection) (sort-by :order humanizers)))))