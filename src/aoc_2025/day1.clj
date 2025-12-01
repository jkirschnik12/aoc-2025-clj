(ns aoc-2025.day1
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")

(def input (slurp (io/resource "day1.txt")))

(def start 50)
(def low 0)
(def high 99)

(defn circular-inc
  [pos x]
  (let [n (+ pos x)]
    (if (> n high)
      (dec (- n high))
      n)))

(defn circular-dec
  [pos x]
  (let [n (- pos  x)]
    (if (neg? n)
      (- high (dec (- low n)))
      n)))

(defn parse
  [in]
  (->> (str/split-lines in)
       (mapv (fn [[dir & dist]]
               [(case (str dir)
                  "R" circular-inc
                  "L" circular-dec)
                (-> dist
                    str/join
                    str/trim
                    Integer/parseInt
                    (rem 100))]))))

(defn solve
  [in]
  (let [in (parse in)]
    (->> in
         (reductions (fn [pos [dir-fn dist]]
                       (dir-fn pos dist))
                     start)
         (filter zero?)
         count)))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input)
  (solve (slurp (io/resource "day1.txt"))))
