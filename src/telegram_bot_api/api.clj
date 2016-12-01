(ns telegram-bot-api.api
  (:require [clojure.spec :as s]
            [telegram-bot-api.core :as tcore]))

;; Getting Updates ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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


(defn get-updates
  [& args]
  (let [resp (call :get-updates (s/conform ::get-updates-args args))]
    (if (:ok resp)
      (:result resp)
      resp)))

(s/def ::offset int?)
(s/def ::limit int?)
(s/def ::timeout int?)
(s/def ::get-updates-args
  (s/keys* :opt-un [::offset ::limit ::timeout]))
(s/fdef get-updates
        :args ::get-updates-args
        :ret (s/or :success (s/coll-of ::update)
                   :failure ::tcore/result-error))

(comment
  (s/conform ::get-updates-args [:offset 10])
  )


(defn set-webhook
  [& args]
  (let [resp (call :set-webhook (s/conform ::get-updates-args args))]
    (if (:ok resp)
      (:result resp)
      resp)))
