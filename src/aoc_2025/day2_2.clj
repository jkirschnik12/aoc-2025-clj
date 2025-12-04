(ns aoc-2025.day2-2
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(def input (slurp (io/resource "day2.txt")))

(defn parse
  [in]
  (->> (str/split in #",")
       (mapv #(str/split % #"-"))))

(defn valid?
  [val]
  (let [num val
        l   (count val)
        m   (quot l 2)]
    (->> (map inc (range m))
         (map #(vector % %))
         (some #(apply = (partition-all (first %) (second %) num))))))

(defn solve
  "Should only cafre about even ones.
  "
  [in]
  
  (let [in (parse in)]
    (->> in
         (into [] (comp (map #(map edn/read-string %))
                        (mapcat #(range (first %) (inc (second %))))
                        (map str)
                        (filter valid?)
                        (map edn/read-string)))
         (apply +))))

(comment
  (valid? "2121212118")
  (valid? "123123")
  (parse sample)
  (solve sample)
  (parse input)
  (solve input))
