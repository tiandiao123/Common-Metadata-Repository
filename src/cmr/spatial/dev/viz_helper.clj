(ns cmr.spatial.dev.viz-helper
  (:require [earth.driver :as earth-viz]
            [vdd-core.core :as vdd]
            [cmr.common.lifecycle :as lifecycle]
            [cmr.spatial.point :as p]
            [cmr.spatial.polygon :as poly]
            [cmr.spatial.mbr :as m]
            [cmr.spatial.arc :as a]
            [cmr.spatial.ring :as r]
            [clojure.string :as s]
            [cmr.spatial.derived :as d]
            [cmr.spatial.math :refer :all])
  (:import cmr.spatial.point.Point
           cmr.spatial.ring.Ring
           cmr.spatial.polygon.Polygon
           cmr.spatial.mbr.Mbr
           cmr.spatial.arc.Arc))

(comment

  (add-geometries
    [(p/point 0 0)
     (p/point 1 1)
     (p/point 2 2)])

  (add-geometries
    [(assoc
       (r/ring [(p/point 1.0 1.0)
                     (p/point 1.0 4.0)
                     (p/point -1.0 1.0)
                     (p/point -2.0 1.0)
                     (p/point -1.0 0.0)
                     (p/point 1.0 1.0)])
       :display-options {:style {:color "9918A0ff" :width 5}})])

  (clear-geometries)

  )

;; Allows starting and stopping the visualization server.
(defrecord VizServer [config server]

  lifecycle/Lifecycle

  (start
    [this system]
    (assoc this :server (vdd/start-viz config)))

  (stop
    [this system]
    (vdd/stop-viz server)
    (dissoc this :server)))


(defn create-viz-server
  "Creates a visualization server which responds to lifecycle start and stop."
  []
  (let [config (assoc (vdd/config) :plugins ["earth"])]
    (->VizServer config nil)))

(defprotocol CmrSpatialToVizGeom
  "Protocol defining functions for converting a cmr spatial type to geometry that can be visualized."
  (cmr-spatial->viz-geoms
    [cmr-spatial]
    "Converts a CMR Spatial shape into a geometry that can be visualized"))

(extend-protocol CmrSpatialToVizGeom
  Point
  (cmr-spatial->viz-geoms
    [point]
    (let [{:keys [lon lat]} point
          label (str (round 2 lon) "," (round 2 lat))
          balloon label]
      [{:type :point
        :lon lon
        :lat lat
        :label label
        :balloon balloon}]))
  Ring
  (cmr-spatial->viz-geoms
    [ring]
    (let [{:keys [points mbr display-options draggable]} ring
          type (if draggable :draggable-ring :ring)]
      [{:type type
        :ords (p/points->ords points)
        :displayOptions display-options}]))

  Arc
  (cmr-spatial->viz-geoms
    [arc]
    (let [{:keys [display-options draggable]} arc
          type (if draggable :draggable-ring :ring)]
      [{:type type
        :ords (a/arc->ords arc)
        :displayOptions display-options}]))

  Polygon
  (cmr-spatial->viz-geoms
    [polygon]
    (let [{:keys [rings display-options]} polygon]
      (mapcat cmr-spatial->viz-geoms (map #(assoc % :display-options display-options) rings))))

  Mbr
  (cmr-spatial->viz-geoms
    [mbr]
    (let [{:keys [west north east south]} mbr]
      [{:type :bounding-rectangle
        :west west
        :north north
        :east east
        :south south}])))

(defn clear-geometries
  "Removes any displayed visualizations in the geometry"
  []
  (earth-viz/clear-viz-geometries))

(defn add-geometries
  "Adds spatial geometry to the visualization. The geometries passed in should be CMR Spatial areas.
  They will be converted into the geometry that can be displayed."
  [geometries]
  (->> geometries
       (map d/calculate-derived)
       (mapcat cmr-spatial->viz-geoms)
       earth-viz/add-viz-geometries))
