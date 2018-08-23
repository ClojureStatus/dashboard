(ns clojars.core
  (:require
    [clj-http.client :as client]
    [clojure.data.json :as json]
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

(defn by-last [a b]
  (compare (last b) (last a)))

(defn downloads-by-version [pkgs]
  (->> pkgs
       (map mpkg)
       (mapcat identity)
       (sort by-last)))

(defn sumpkg [[pkg vers]]
  (conj pkg (apply + (vals vers))))

(defn downloads-by-package [pkgs]
  (->> pkgs
       (map sumpkg)
       (sort by-last)))

(defn -main
  [statsfile & args]
  (let [pkgs (get-download-stats statsfile)]
    (pp/pprint (take 20 (downloads-by-version pkgs)))
    (pp/pprint (take 20 (downloads-by-package pkgs)))))
