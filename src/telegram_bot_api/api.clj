(ns telegram-bot-api.api
  (:require [clojure
             [spec :as s]]
            [telegram-bot-api
             [core :as tcore :refer [call]]
             [specs :as specs]]))

;; Getting Updates

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
        :ret (s/or :success (s/coll-of ::specs/update)
                   :failure ::tcore/result-error))

;; Setting Webhook

(defn set-webhook
  [& args]
  (let [resp (call :set-webhook (s/conform ::get-updates-args args))]
    (if (:ok resp)
      (:result resp)
      resp)))
