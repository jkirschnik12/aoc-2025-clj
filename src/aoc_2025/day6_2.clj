(ns aoc-2025.day6-2
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
  (let [lines (str/split-lines in)
        nums (butlast lines)
        ops (last lines)
        line-cnt (count lines)]
    (loop [[op & rem] ops
           nums nums
           offset 0
           acc []]
      (if-not op
        acc
        (let [nxt (take-while #(= \space %) rem)
              nxt-count (count nxt)
              spaces (if (first (drop nxt-count rem))
                       nxt-count
                       (inc nxt-count))
              new-nums (map (fn [i]
                              (edn/read-string (str/trim (str/join (map (fn [j]
                                                                          (nth (nth lines j) i))
                                                                        (range (dec line-cnt)))))))
                            (range offset
                                   (+ offset spaces)))
              data {:op   op
                    :nums new-nums}
              nxt-ops (drop spaces rem)]
          (recur nxt-ops
                 (map (partial drop (inc spaces)) nums)
                 (+ offset (inc spaces))
                 (conj acc data)))))))
(defn solve
  [in]
  (let [in (parse in)]
    (->> in
         (map (fn [{:keys [op nums]}]
                (->> nums
                     (apply (case op \+ + \* *)))))
         (apply +))))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input))

