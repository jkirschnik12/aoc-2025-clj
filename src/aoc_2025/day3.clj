(ns aoc-2025.day3
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "987654321111111
811111111111119
234234234234278
818181911112111")

(def input (slurp (io/resource "day3.txt")))

(defn find-max-joltage
  [bat-pak]
  (let [mx (apply max (butlast bat-pak))]
    (->> bat-pak
         (split-with (complement #{mx}))
         second
         rest
         (apply max)
         (str mx))))

(defn parse
  [in]
  (->> (str/split-lines in)
       (map #(str/split % #""))
       (map #(map edn/read-string %))))

(defn solve
  "Should only cafre about even ones.
  "
  [in]
  (let [in (parse in)]
    (->> in
         (map find-max-joltage)
         (map edn/read-string)
         (apply +))))

(comment

  (map find-max-joltage (parse sample))
  (solve sample)
  (parse input)
  (solve input)
  )
