(ns telegram-bot-api.core
  (:require [aleph.http :as http]
            [byte-streams :as bs]
            [camel-snake-kebab.core :as csk]
            [cheshire.core :as json]
            [manifold.deferred :as d]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [clojure.spec.test :as stest]
            [clojure.string]))



;; API Token ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def ^:dynamic token (System/getenv "TELEGRAM_BOT_TOKEN"))

(defmacro with-token
  "Set the bot token for the duration of body"
  [tok & body]
  `(binding [token ~tok] ~@body))


;; Get Endpoint Address ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-endpoint
  "Get the normalized endpoint for an API call."
  [endpoint]
  (let [ep (csk/->camelCaseString endpoint)]
    (str "https://api.telegram.org/bot" token "/" ep)))

(s/def ::getting-updates-endpoints
  #{:get-updates :set-webhook :get-webhook-info})

(s/def ::available-methods-endpoints
  #{:get-me :send-message :forward-message :send-photo :send-audio
   :send-document :send-sticker :send-video :send-voice :send-location
   :send-venue :send-contact :send-chat-action :get-user-profile-photos
   :get-file :kick-chat-member :leave-chat :unban-chat-member :get-chat
   :get-chat-administrators :get-chat-members-count :get-chat-member
   :answer-callback-query})

(s/def ::updating-messages-endpoints
  #{:edit-message-text :edit-message-caption :edit-message-reply-markup})

(s/def ::inline-mode-endpoints
  #{:answer-inline-query})

(s/def ::games-endpoints
  #{:send-game :set-game-score :get-game-high-scores})

(s/def ::endpoints (s/or :getting-updates ::getting-updates-endpoints
                         :available-methods ::available-methods-endpoints
                         :updating-messages ::updating-messages-endpoints
                         :inline-mode ::inline-mode-endpoints
                         :games ::games-endpoints))

(s/fdef get-endpoint
        :args (s/cat :endpoint ::endpoints)
        :ret string?
        :fn #(<=
              (-> % :ret
                  (clojure.string/replace-first #".*/([^/]*)$" "$1") count)
              (-> % :args :endpoint second name count)))

(comment
  (s/exercise-fn `get-endpoint)
  (stest/summarize-results (stest/check `get-endpoint))
  )


;; API Call Result Type ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [ns-str (str *ns*)]
  (defn parse-response
    "Parse the result of an api call."
    [response]
    (-> response
        :body
        bs/to-string
        (json/decode #(->> %
                           csk/->kebab-case-string
                           (keyword ns-str))))))

(s/def ::body
  (s/with-gen (s/and string? json/decode)
    (fn [] (gen/fmap json/encode (s/gen ::result)))))
(s/def ::response (s/keys :req-un [::body]))
(s/def ::result-success
  (s/with-gen #(:ok %)
    (fn [] (gen/fmap #(assoc % :ok true) (s/gen map?)))))
(s/def ::result-error
  (s/with-gen (complement :ok)
    (fn [] (gen/fmap #(assoc % :ok false) (s/gen map?)))))
(s/def ::result
  (s/or :success ::result-success
         :error ::result-error))
(s/fdef parse-response
        :args (s/cat :response ::response)
        :ret ::result)

(comment
  (s/exercise ::body)
  (s/exercise ::response)
  (s/exercise ::result)
  (s/exercise-fn `parse-response)
  (stest/summarize-results (stest/check `get-endpoint))
  )


;; Call Telegram API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn call
  "Call to a telegram api endpoint and retern an IResult."
  ([endpoint] (parse-response @(http/get (get-endpoint endpoint))))
  ([endpoint params]
   (parse-response @(http/post (get-endpoint endpoint)
                             {:form-params params
                              :content-type :json
                              :accept :json}))))

(comment
  (with-token boot.user/token
    (call :get-me))
  )
