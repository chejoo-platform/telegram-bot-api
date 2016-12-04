(ns telegram-bot-api.specs
  (:require [clojure.spec :as s]
            [telegram-bot-api.core :as tcore]))


(s/def ::message any?)
(s/def ::inline-query any?)
(s/def ::chosen-inline-result any?)
(s/def ::callback-query any?)

(s/def ::tcore/update-id int?)
(s/def ::tcore/message ::message)
(s/def ::tcore/edited-message ::message)
(s/def ::tcore/channel-post ::message)
(s/def ::tcore/edited-channel-post ::message)
(s/def ::tcore/inline-query ::inline-query)
(s/def ::tcore/chosen-inline-result ::chosen-inline-result)
(s/def ::tcore/callback-query ::callback-query)

(s/def ::update (s/keys :req [::tcore/update-id]
                        :opt [::tcore/message ::tcore/edited-message
                              ::tcore/channel-post ::tcore/edited-channel-post
                              ::tcore/inline-query ::tcore/chosen-inline-result
                              ::tcore/callback-query]))

(s/def ::tcore/url string?)
(s/def ::tcore/has-custom-certificate boolean?)
(s/def ::tcore/pending-update-count int?)
(s/def ::tcore/last-error-date int?)
(s/def ::tcore/last_error_message string?)

(s/def ::webhook-info (s/keys :req [::tcore/url ::tcore/has-custom-certificate
                                    ::tcore/pending-update-count]
                              :opt [::tcore/last-error-date
                                    ::tcore/last_error_message]))
