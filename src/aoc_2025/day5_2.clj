(ns aoc-2025.day5-2
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

(defn merge-range
  [r1 r2]
  (let [[[l1 h1] [l2 h2]] (sort-by first [r1 r2])]
    (when (<= l2 h1)
      [l1 (max h1 h2)])))

(defn consolidate-ranges
  "oooooooooooof this needs to be cleaned up"
  [ranges]
  (let [ranges (vec (sort-by first ranges))
        cntr (count ranges)]
    (loop [i 0
           acc ranges]
      (let [cnt (count acc)
            cur (nth ranges i nil)]
        (if (>= i cntr)
          acc
          (recur
           (inc i)
           (loop [j 0
                  cur   cur
                  acc-2 []]
             (let [curj (nth acc j nil)]
               (if (>= j cnt)
                 (conj acc-2 cur)
                 (let [result (merge-range cur curj)]
                   (recur (inc j)
                          (or result cur)
                          (if result
                            acc-2
                            (conj acc-2 curj)))))))))))))

(defn solve
  [in]
  (let [[fresh _available] (parse in)]
    (->> (consolidate-ranges fresh)
         (reduce (fn [acc [l h]]
                   (+ acc (inc (- h l)))) 0))))

(comment

  (consolidate-ranges (first (parse sample)))
  (merge-range [3 5] [1 100])
  (solve sample)
  (parse input)
  (solve input))

