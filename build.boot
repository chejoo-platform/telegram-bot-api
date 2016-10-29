
(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.9.0-alpha13" :scope "provided"]
                 [camel-snake-kebab "0.4.0"]
                 [org.clojure/test.check "0.9.0" :scope "test"]
                 [aleph "0.4.2-alpha8"]
                 [cheshire "5.6.3"]])

(task-options!
 pom {:project 'org.clojars.mkhoeini/telegram-bot-api
      :version "0.1.0-SNAPSHOT"})

(deftask build
  "Build my project."
  []
  (comp (pom) (jar) (install)))
