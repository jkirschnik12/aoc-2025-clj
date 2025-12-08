(ns aoc-2025.day6
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ")

(def input (slurp (io/resource "day6.txt")))

(defn parse
  [in]
  (let [lines (->> (str/split-lines in)
                   (mapv #(re-seq #"\d+|[\*\+]" %)))
        lines-cnt (count lines)
        line-len (count (first lines))]
    (mapv (fn [i]
            (reverse
             (mapv
              (fn [j]
                (edn/read-string
                 (nth (nth lines j) i)))
              (range lines-cnt))))
          (range line-len))))

(defn solve
  [in]
  (let [in (parse in)]
    (->> in
         (mapv eval)
         (apply +))))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input)
  )

