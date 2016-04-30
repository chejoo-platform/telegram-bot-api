(ns chjoori-bot-server.core
  (:require [aleph.http :as http]
            [byte-streams :as bs]
            [camel-snake-kebab.core :as csk]
            [cheshire.core :as json]
            [manifold.deferred :as d]))

(def token (System/getenv "BOT_TOKEN"))

(defn get-endpoint
  [endpoint]
  (csk/->camelCaseString endpoint))

(defn call
  "Call to a telegram api endpoint and retern a deffered for result."
  ([endpoint]
   (http/get (str "https://api.telegram.org/bot" token "/" (get-endpoint endpoint))))
  ([endpoint params]
   (http/post (str "https://api.telegram.org/bot" token "/" (get-endpoint endpoint))
              {:form-params params
               :content-type :json
               :accept :json})))

(defprotocol IResult
  "Result of the api call."
  (success? [this] "Was it successful or failure.")
  (result [this] "In case of success return the result.")
  (error [this] "In case of failure returen the error."))

(defn parse-result
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
