(ns telegram-bot-api.specs
  (:require [clojure.spec :as s]
            [telegram-bot-api.core :as tcore]))


(s/def ::tcore/all-members-are-administrators boolean?)
(s/def ::tcore/callback-query ::callback-query)
(s/def ::tcore/channel-post ::message)
(s/def ::tcore/chosen-inline-result ::chosen-inline-result)
(s/def ::tcore/edited-channel-post ::message)
(s/def ::tcore/edited-message ::message)
(s/def ::tcore/first-name string?)
(s/def ::tcore/has-custom-certificate boolean?)
(s/def ::tcore/id int?)
(s/def ::tcore/inline-query ::inline-query)
(s/def ::tcore/last-error-date int?)
(s/def ::tcore/last-name string?)
(s/def ::tcore/last_error_message string?)
(s/def ::tcore/message ::message)
(s/def ::tcore/pending-update-count int?)
(s/def ::tcore/title string?)
(s/def ::tcore/type string?)
(s/def ::tcore/update-id int?)
(s/def ::tcore/url string?)
(s/def ::tcore/username string?)

(s/def ::message any?)

(s/def ::inline-query any?)
(s/def ::chosen-inline-result any?)
(s/def ::callback-query any?)

(s/def ::update (s/keys :req [::tcore/update-id]
                        :opt [::tcore/message ::tcore/edited-message
                              ::tcore/channel-post ::tcore/edited-channel-post
                              ::tcore/inline-query ::tcore/chosen-inline-result
                              ::tcore/callback-query]))

(s/def ::webhook-info (s/keys :req [::tcore/url ::tcore/has-custom-certificate
                                    ::tcore/pending-update-count]
                              :opt [::tcore/last-error-date
                                    ::tcore/last_error_message]))

(s/def ::user (s/keys :req [::tcore/id ::tcore/first-name]
                      :opt [::tcore/last-name ::tcore/username]))

(s/def ::chat (s/keys :req [::tcore/id ::tcore/type]
                      :opt [::tcore/title ::tcore/username
                            ::tcore/first-name ::tcore/last-name
                            ::tcore/all-members-are-administrators]))
