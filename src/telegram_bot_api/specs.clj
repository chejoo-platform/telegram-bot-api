(ns telegram-bot-api.specs
  (:require [clojure.spec :as s]
            [telegram-bot-api.core :as tcore]))


(s/def ::tcore/all-members-are-administrators boolean?)
(s/def ::tcore/audio ::audio)
(s/def ::tcore/callback-query ::callback-query)
(s/def ::tcore/caption string?)
(s/def ::tcore/channel-chat-created true?)
(s/def ::tcore/channel-post ::message)
(s/def ::tcore/chat ::chat)
(s/def ::tcore/chosen-inline-result ::chosen-inline-result)
(s/def ::tcore/contact ::contact)
(s/def ::tcore/date int?)
(s/def ::tcore/delete-chat-photo true?)
(s/def ::tcore/document ::document)
(s/def ::tcore/edit-date int?)
(s/def ::tcore/edited-channel-post ::message)
(s/def ::tcore/edited-message ::message)
(s/def ::tcore/entities (s/coll-of ::message-entity))
(s/def ::tcore/first-name string?)
(s/def ::tcore/forward-date int?)
(s/def ::tcore/forward-from ::user)
(s/def ::tcore/forward-from-chat ::chat)
(s/def ::tcore/forward-from-message-id int?)
(s/def ::tcore/from ::user)
(s/def ::tcore/game ::game)
(s/def ::tcore/group-chat-created true?)
(s/def ::tcore/has-custom-certificate boolean?)
(s/def ::tcore/id int?)
(s/def ::tcore/inline-query ::inline-query)
(s/def ::tcore/last-error-date int?)
(s/def ::tcore/last-name string?)
(s/def ::tcore/last_error_message string?)
(s/def ::tcore/left-chat-member ::user)
(s/def ::tcore/location ::location)
(s/def ::tcore/message ::message)
(s/def ::tcore/message-id int?)
(s/def ::tcore/migrate-from-chat-id int?)
(s/def ::tcore/migrate-to-chat-id int?)
(s/def ::tcore/new-chat-member ::user)
(s/def ::tcore/new-chat-photo (s/coll-of ::photo-size))
(s/def ::tcore/new-chat-title string?)
(s/def ::tcore/pending-update-count int?)
(s/def ::tcore/pinned-message ::message)
(s/def ::tcore/photo (s/coll-of ::photo-size))
(s/def ::tcore/reply-to-message ::message)
(s/def ::tcore/sticker ::sticker)
(s/def ::tcore/supergroup-chat-created true?)
(s/def ::tcore/text string?)
(s/def ::tcore/title string?)
(s/def ::tcore/type string?)
(s/def ::tcore/update-id int?)
(s/def ::tcore/url string?)
(s/def ::tcore/username string?)
(s/def ::tcore/venue ::venue)
(s/def ::tcore/video ::video)
(s/def ::tcore/voice ::voice)

(s/def ::message (s/keys :req [::tcore/message-id ::tcore/from ::tcore/date
                               ::tcore/chat]
                         :opt [::tcore/forward-from ::tcore/forward-from-chat
                               ::tcore/forward-from-message-id ::tcore/forward-date
                               ::tcore/reply-to-message ::tcore/edit-date
                               ::tcore/text ::tcore/entities ::tcore/audio
                               ::tcore/document ::tcore/game ::tcore/photo
                               ::tcore/sticker ::tcore/video ::tcore/voice
                               ::tcore/caption ::tcore/contact ::tcore/location
                               ::tcore/venue ::tcore/new-chat-member
                               ::tcore/left-chat-member ::tcore/new-chat-title
                               ::tcore/new-chat-photo ::tcore/delete-chat-photo
                               ::tcore/group-chat-created
                               ::tcore/supergroup-chat-created
                               ::tcore/channel-chat-created
                               ::tcore/migrate-to-chat-id
                               ::tcore/migrate-from-chat-id
                               ::tcore/pinned-message]))

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
