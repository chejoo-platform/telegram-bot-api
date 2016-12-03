(ns telegram-bot-api.specs
  (:require [clojure.spec :as s]))


(s/def ::message any?)
(s/def ::inline-query any?)
(s/def ::chosen-inline-result any?)
(s/def ::callback-query any?)

(s/def ::tcore/update-id int?)
(s/def ::tcore/message ::message)
(s/def ::tcore/edited-message ::message)
(s/def ::tcore/inline-query ::inline-query)
(s/def ::tcore/chosen-inline-result ::chosen-inline-result)
(s/def ::tcore/callback-query ::callback-query)

(s/def ::update (s/keys :req [::tcore/update-id]
                        :opt [::tcore/message ::tcore/edited-message
                              ::tcore/inline-query ::tcore/chosen-inline-result
                              ::tcore/callback-query]))
