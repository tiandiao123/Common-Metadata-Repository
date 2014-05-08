(ns cmr.system-int-test.search.collection-sorting-search-test
  "Tests searching for collections using basic collection identifiers"
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [cmr.system-int-test.utils.ingest-util :as ingest]
            [cmr.system-int-test.utils.search-util :as search]
            [cmr.system-int-test.utils.index-util :as index]
            [cmr.system-int-test.data2.collection :as dc]
            [cmr.system-int-test.data2.core :as d]
            [cmr.search.services.messages.common-messages :as msg]))


(use-fixtures :each (ingest/reset-fixture "PROV1" "PROV2"))

(defn make-coll
  "Helper for creating and ingesting a collection"
  [provider entry-title begin end]
  (d/ingest provider
            (dc/collection {:entry-title entry-title
                            :beginning-date-time (d/make-datetime begin)
                            :ending-date-time (d/make-datetime end)})))

(deftest invalid-sort-key-test
  (is (= {:status 422
          :errors [(msg/invalid-sort-key "foo_bar" :collection)]}
         (search/find-refs :collection {:sort-key "foo_bar"}))))

(deftest sorting-test
  (let [c1 (make-coll "PROV1" "et99" 10 20)
        c2 (make-coll "PROV1" "et90" 14 24)
        c3 (make-coll "PROV1" "et80" 19 30)
        c4 (make-coll "PROV1" "et70" 24 35)

        c5 (make-coll "PROV2" "et98" 9 19)
        c6 (make-coll "PROV2" "et91" 15 25)
        c7 (make-coll "PROV2" "et79" 20 29)
        c8 (make-coll "PROV2" "ET94" 25 36)

        c9 (make-coll "PROV1" "et95" nil nil)
        c10 (make-coll "PROV2" "et85" nil nil)
        c11 (make-coll "PROV1" "et96" 12 nil)
        c12 (make-coll "PROV2" "et86" nil 22)
        all-colls [c1 c2 c3 c4 c5 c6 c7 c8 c9 c10 c11 c12]]
    (index/flush-elastic-index)

    (testing "default sorting"
      (is (d/refs-match-order?
            (sort-by (comp str/lower-case :entry-title) all-colls)
            (search/find-refs :collection {:page-size 20}))))

    (testing "Sort by entry title ascending"
      (are [sort-key] (d/refs-match-order?
                        (sort-by (comp str/lower-case :entry-title) all-colls)
                        (search/find-refs :collection {:page-size 20
                                                       :sort-key sort-key}))
           "entry_title"
           "+entry_title"
           "dataset_id" ; this is an alias for entry title
           "+dataset_id"))

    (testing "Sort by entry title descending"
      (are [sort-key] (d/refs-match-order?
                        (reverse (sort-by (comp str/lower-case :entry-title) all-colls))
                        (search/find-refs :collection {:page-size 20 :sort-key sort-key}))
           "-entry_title"
           "-dataset_id"))

     (testing "temporal start date"
      (are [sort-key items] (d/refs-match-order?
                              items
                              (search/find-refs :collection {:page-size 20
                                                             :sort-key sort-key}))
           "start_date" [c5 c1 c11 c2 c6 c3 c7 c4 c8 c9 c10 c12]
           "-start_date" [c8 c4 c7 c3 c6 c2 c11 c1 c5 c9 c10 c12]))

    (testing "temporal end date"
      (are [sort-key items] (d/refs-match-order?
                              items
                              (search/find-refs :collection {:page-size 20
                                                             :sort-key sort-key}))
           "end_date" [c5 c1 c12 c2 c6 c7 c3 c4 c8 c9 c10 c11]
           "-end_date" [c8 c4 c3 c7 c6 c2 c12 c1 c5 c9 c10 c11]))))

(deftest multiple-sort-key-test
  (let [c1 (make-coll "PROV1" "et10" 10 nil)
        c2 (make-coll "PROV1" "et20" 10 nil)
        c3 (make-coll "PROV1" "et30" 10 nil)
        c4 (make-coll "PROV1" "et40" 10 nil)

        c5 (make-coll "PROV2" "et10" 20 nil)
        c6 (make-coll "PROV2" "et20" 20 nil)
        c7 (make-coll "PROV2" "et30" 20 nil)
        c8 (make-coll "PROV2" "et40" 20 nil)]
    (index/flush-elastic-index)

    (are [sort-key items] (d/refs-match-order?
                            items
                            (search/find-refs :collection {:page-size 20
                                                           :sort-key sort-key}))
         ["entry_title" "start_date"] [c1 c5 c2 c6 c3 c7 c4 c8]
         ["entry_title" "-start_date"] [c5 c1 c6 c2 c7 c3 c8 c4]
         ["start_date" "entry_title"] [c1 c2 c3 c4 c5 c6 c7 c8]
         ["start_date" "-entry_title"] [c4 c3 c2 c1 c8 c7 c6 c5]
         ["-start_date" "entry_title"] [c5 c6 c7 c8 c1 c2 c3 c4]

         ;; Tests provider sorting for collections
         ["provider" "-entry_title"] [c4 c3 c2 c1 c8 c7 c6 c5]
         ["-provider" "-entry_title"] [c8 c7 c6 c5 c4 c3 c2 c1])))
