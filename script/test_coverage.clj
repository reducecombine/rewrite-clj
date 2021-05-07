#!/usr/bin/env bb

(ns test-coverage
  (:require [helper.env :as env]
            [helper.main :as main]
            [helper.shell :as shell]
            [lread.status-line :as status]))

(defn generate-doc-tests []
  (status/line :head "Generating tests for code blocks in documents")
  (shell/command ["clojure" "-X:test-doc-blocks" "gen-tests"]))

(defn run-clj-doc-tests []
  (status/line :head "Running unit and code block tests under Clojure for coverage report")
  (shell/command ["clojure" "-M:test-common:test-docs:kaocha"
                  "--plugin" "cloverage" "--codecov"
                  "--profile" "coverage"
                  "--no-randomize"
                  "--reporter" "documentation"]))

(defn -main [& args]
  (main/run-argless-cmd
   args
   (fn []
     (generate-doc-tests)
     (run-clj-doc-tests)))
  nil)

(env/when-invoked-as-script
 (apply -main *command-line-args*))