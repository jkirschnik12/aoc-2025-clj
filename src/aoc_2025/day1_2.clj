(ns aoc-2025.day1-2
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
      [true (dec (- n high))]
      [false n])))

(defn circular-dec
  [pos x]
  (let [n (- pos  x)]
    (if (neg? n)
      [true (- high (dec (- low n)))]
      [false n])))

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
                    Integer/parseInt)]))))

(defn solve
  [in]
  (let [in (parse in)]
    (->> in
         (reduce (fn [{:keys [pos zeros]} [dir-fn dist]]
                   (let [real-dist         (rem dist 100)
                         zeros             (+ zeros (quot dist 100))
                         [circle? new-pos] (dir-fn pos real-dist)]
                     {:pos   new-pos
                      :zeros (if (or (and circle?
                                          (not (zero? pos)))
                                     (zero? new-pos)) 
                               (inc zeros)
                               zeros)}))
                 {:pos start :zeros 0})
         :zeros)))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input))
