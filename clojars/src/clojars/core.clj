(ns clojars.core
  (:require
    [clj-http.client :as client]
    [clojure.pprint :as pp]
    [clojure.edn :as edn])
  (:gen-class))

(defn get-download-stats
  ([]
   (get-download-stats "all.edn"))

  ([filename]
   (->> (client/get (str "https://clojars.org/stats/" filename))
        (:body)
        (edn/read-string))))

(defn mpkg [[pkg vers]]
  (for [ver vers]
    (into pkg ver)))

(defn sort-pkgs [pkgs]
  (->> pkgs
       (map mpkg)
       (mapcat identity)
       (sort #(compare (last %2) (last %1)))))

(defn -main
  [statsfile & args]
  (let [pkgs (get-download-stats statsfile)]
    (pp/pprint (take 25 (sort-pkgs pkgs)))))
