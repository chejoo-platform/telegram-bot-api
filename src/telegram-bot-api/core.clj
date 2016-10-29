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

(s/fdef get-endpoint
        :args (s/cat :endpoint keyword?)
        :ret string?
        :fn #(<=
              (-> % :ret (clojure.string/replace-first #".*/([^/]*)$" "$1") count)
              (-> % :args :endpoint name count)))

(comment
  (s/exercise-fn `get-endpoint)
  (stest/summarize-results (stest/check `get-endpoint))
  )


;; API Call Result Type ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defprotocol IResult
  "This protocol represents the result of an API call."
  (success? [this] "Was it successful or failure.")
  (result [this] "In case of success return the result.")
  (error [this] "In case of failure returen the error."))

(defn parse-result
  "Parse the result of an api call."
  [deffered]
  (let [req (d/chain deffered
                     :body
                     bs/to-string
                     #(json/parse-string % true))]
    (reify
      IResult
      (success? [_] (d/chain req :ok))
      (result [_] (d/chain req
                           #(if (:ok %)
                              (:result %))))
      (error [_] (d/chain req
                          #(if-not (:ok %) (:description %)))))))

(s/fdef parse-result
        :args (s/cat :deffered (s/with-gen d/deferred?
                                 (gen/fmap #(d/future %) (gen/any))))
        :ret (partial instance? IResult))

(comment
  (s/exercise-fn `parse-result)
  (stest/summarize-results (stest/check `get-endpoint))
  )


;; Call Telegram API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn call
  "Call to a telegram api endpoint and retern an IResult."
  ([endpoint] (parse-result (http/get (get-endpoint endpoint))))
  ([endpoint params]
   (parse-result (http/post (get-endpoint endpoint)
                            {:form-params params
                             :content-type :json
                             :accept :json}))))
