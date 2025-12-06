(ns aoc-2025.day5
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "3-5
10-14
16-20
12-18

1
5
8
11
17
32")

(def input (slurp (io/resource "day5.txt")))

(defn parse
  [in]
  (let [[fresh available] (->> (str/split in #"\n\n")
                               (map str/split-lines))]
    [(mapv #(mapv edn/read-string (str/split % #"-")) fresh)
     (mapv edn/read-string available)]))

(defn solve
  [in]
  (let [[fresh available] (parse in)]
    (->> available
         (filter #(some (fn fresh?
                          [[low high]]
                          (<= low % high)) fresh))
         count)
    ))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input)
  )

