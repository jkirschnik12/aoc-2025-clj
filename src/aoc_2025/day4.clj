(ns aoc-2025.day4
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.")

(def input (slurp (io/resource "day4.txt")))

(defn parse
  [in]
  (->> (str/split-lines in)
       (map #(str/split % #""))
       (mapv vec)))

(defn next-coord
  [[y x] lx]
  (if (>= x (dec lx))
    [(inc y) 0]
    [y (inc x)]))

(defn get-valid-neighbors
  [[y x] ly lx]
  (->> [[(dec y) x]
        [(dec y) (dec x)]
        [(dec y) (inc x)]
        [(inc y) x]
        [(inc y) (dec x)]
        [(inc y) (inc x)]
        [y (dec x)]
        [y (inc x)]]
       (filter (fn [[y x]]
                 (and (nat-int? y)
                      (nat-int? x)
                      (< y ly)
                      (< x lx))))))

(defn sweep
  [fl-map [y x] ly lx]
  (->> (get-valid-neighbors [y x] ly lx)
       (filter #(= "@" (get-in fl-map %)))
       count))

(defn solve*
  [fl-map]
  (let [ly (count fl-map)
        lx (count (first fl-map))]
    (loop [sol-map fl-map
           [y x] [0 0]
           cnt 0]
      #_(println {:y y :x x :cnt cnt :sol-map sol-map}
               (get-in fl-map [y x]))
      (if-let [pos (get-in fl-map [y x])]
        (if (= pos ".")
          (recur sol-map (next-coord [y x] lx) cnt)
          (let [neighbor-rolls (sweep fl-map [y x] ly lx)
                valid? (< neighbor-rolls 4)
                sol-map (if-not valid?
                          sol-map
                          (assoc-in sol-map [y x] "x"))
                cnt (if valid? (inc cnt) cnt)]
            (recur sol-map (next-coord [y x] lx) cnt)))
        cnt))))

(defn solve
  "Should only cafre about even ones.
  "
  [in]
  (let [in (parse in)]
    (->> in
         solve*)))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input)1508
  )

