(ns aoc-2025.dayx
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def sample
  "")

(def input (slurp (io/resource "dayx.txt")))

(defn parse
  [in]
  (->> (str/split-lines in)))

(defn solve
  [in]
  (let [in (parse in)]
    (->> in)))

(comment
  (parse sample)
  (solve sample)
  (parse input)
  (solve input))

