(ns aoc-2025.day2
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
  (let [l    (count val)
        m    (quot l 2)
        pre  (subs val 0 m)
        suf  (subs val m)]
    (= pre suf)))

(defn idk
  [start]
  (let [l (count start)
        m (quot l 2)
        pre (subs start 0 m)
        suf (subs start m)
        pren (edn/read-string pre)
        sufn (edn/read-string suf)]
    (cond
      (not= (count suf) (count pre))
      (idk (str 1 (str/join (repeat l 0))))
      
      (<= pren sufn)
      (idk (str (inc pren) (str/join (repeat m 0))))
      
      
      :else
      (str pre pre))))

(defn solve*
  [start end]
  (let [acc (if (valid? start) [start] [])]
   (loop [next start
          acc  acc]
     (if (> (edn/read-string next)
            (edn/read-string end))
       (butlast acc)
       (let [result (idk next)]
         (recur result (conj acc result)))))))

(defn solve
  "Should only cafre about even ones.
  "
  [in]
  (let [in (parse in)]
    (->> in
         (mapcat (fn [[start end]]
                   (solve* start end)))
         (map edn/read-string)
         (apply +))))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input)54234399924
  
  )
