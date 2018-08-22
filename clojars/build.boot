(def project 'clojars)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :offline?       true
          :dependencies   '[[adzerk/boot-test "1.2.0" :scope "test"]
                            [clj-http "3.9.0"]
                            [org.clojure/clojure "1.10.0-alpha6"]
                            [org.clojure/data.json "0.2.6"]
                            [proto-repl "0.3.1" :scopep "test"]])


(task-options!
  aot {:namespace   #{'clojars.core}}
  pom {:project     project
       :version     version
       :description "Clojure status dashboard"
       :url         "https://clojurestatus.github.io/dashboard"
       :scm         {:url "https://github.com/clojurestatus/dashboard"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}}
  repl {:init-ns    'clojars.core}
  jar {:main        'clojars.core
       :file        (str "clojars-" version "-standalone.jar")})

(deftask dev
  "Profile setup for development.
  	Starting the repl with the dev profile...
  	boot dev repl "
  []
  (println "Dev profile running")
  (set-env!
   :init-ns 'user
   :source-paths #(into % ["dev" "test"])
   :dependencies #(into % '[[org.clojure/tools.namespace "0.2.11"]]))

  ;; Makes clojure.tools.namespace.repl work per https://github.com/boot-clj/boot/wiki/Repl-reloading
  (require 'clojure.tools.namespace.repl)
  (eval '(apply clojure.tools.namespace.repl/set-refresh-dirs
                (get-env :directories))))

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (with-pass-thru fs
    (require '[clojars.core :as app])
    (apply (resolve 'app/-main) args)))

(require '[adzerk.boot-test :refer [test]])
