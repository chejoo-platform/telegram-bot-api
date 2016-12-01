(ns chjoori-bot-server.types)



(defmacro tapi-type
  "This macro defines a Telegram API type conjoined with its documentation.

  fields is a seq of field-description s.
  A field-description is like this:
  (field-name type opts? doc)

  opts is a map with potentially these keys:
  :coersion-fn A function to convert from original format in JSON.
  :optional A bool indicating that this field can be absent from JSON.
  :original-name Original name of the field in JSON."
  [name doc-str & fields]
  (let [field-names (map first fields)]
    `(defrecord ~name ~(vec field-names))))


(tapi-type Update
  "This object represents an incoming update.
  Only one of the optional parameters can be present in any given update."
  (id Integer
    {:optional false
     :original-name update_id}
    "The update‘s unique identifier. Update identifiers start from a certain
    positive number and increase sequentially. This ID becomes especially handy
    if you’re using Webhooks, since it allows you to ignore repeated updates or
    to restore the correct update sequence, should they get out of order.")
  (type keyword
    {}
    "Type of update. One of :message, :inline-query, etc.")
  (message IMessage
    {:optional true}
    "New incoming message of any kind — text, photo, sticker, etc.")
  (inline-query IInlineQuery
    {:optional true}
    "New incoming inline query.")
  (chosen-inline-result IChosenInlineResult
    {:optional true}
    "The result of an inline query that was chosen by a user and sent to their
    chat partner.")
  (callback-query ICallbackQuery
    {:optional true}
    "New incoming callback query."))

(defn create-update
  "Create an instance of Update."
  [id type value]
  (-> (->IUpdate id)
    (assoc type value)))


(tapi-type User
  "This object represents a Telegram user or bot."
  (id Integer
    {:original-name }
    "Unique identifier for this user or bot.")
  (first_name String
    {}
    "User‘s or bot’s first name.")
  (last_name String
    {:optional true}
    "User‘s or bot’s last name.")
  (username String
    {:optional true}
    "User‘s or bot’s username."))


(tapi-type Chat
  "This object represents a chat."
  (id Integer
    {}
    "Unique identifier for this chat, not exceeding 1e13 by absolute value.")
  (type String
    {}
    "Type of chat, can be either “private”, “group”, “supergroup” or
    “channel”.")
  (title String
    {:optional true}
    "Title, for channels and group chats.")
  (username String
    {:optional true}
    "Username, for private chats and channels if available.")
  (first-name String
    {:optional true}
    "First name of the other party in a private chat.")
  (last-name String
    {:optional true}
    "Last name of the other party in a private chat."))


(tapi-type Message
  "This object represents a message."
  (id Integer
    {:original-name message_id}
    "Unique message identifier.")
  (from User
    {:optional true}
    "Sender, can be empty for messages sent to channels.")
  (date Integer
    {}
    "Date the message was sent in Unix time.")
  (chat Chat
    {}
    "Conversation the message belongs to.")
  (forward-from User
    {:optional true}
    "For forwarded messages, sender of the original message.")
  (forward-from-chat Chat
    {:optional true}
    "For messages forwarded from a channel, information about the original
    channel.")
  (forward-date Integer
    {:optional true}
    "For forwarded messages, date the original message was sent in Unix time.")
  (reply-to-message Message
    {:optional true}
    "For replies, the original message. Note that the Message object in this
    field will not contain further reply_to_message fields even if it itself is
    a reply.")
  (text String
    {:optional true}
    "For text messages, the actual UTF-8 text of the message, 0-4096
    characters.")
  (entities [MessageEntity]
    {:optional true}
    "For text messages, special entities like usernames, URLs, bot commands,
    etc. that appear in the text.")
  (audio Audio
    {:optional true}
    "Message is an audio file, information about the file.")
  (document Document
    {:optional true}
    "Message is a general file, information about the file.")
  (photo [PhotoSize]
    {:optional true}
    "Message is a photo, available sizes of the photo.")
  (sticker Sticker
    {:optional true}
    "Message is a sticker, information about the sticker.")
  (video Video
    {:optional true}
    "Message is a video, information about the video.")
  (voice Voice
    {:optional true}
    "Message is a voice message, information about the file.")
  (caption String
    {:optional true}
    "Caption for the document, photo or video, 0-200 characters.")
  (contact Contact
    {:optional true}
    "Message is a shared contact, information about the contact.")
  (location Location
    {:optional true}
    "Message is a shared location, information about the location.")
  (venue Venue
    {:optional true}
    "Message is a venue, information about the venue.")
  (new-chat-member User
    {:optional true}
    "A new member was added to the group, information about them (this member
    may be the bot itself).")
  (left-chat-member User
    {:optional true}
    "A member was removed from the group, information about them (this member
    may be the bot itself).")
  (new-chat-title String
    {:optional true}
    "A chat title was changed to this value.")
  (new-chat-photo [PhotoSize]
    {:optional true}
    "A chat photo was change to this value.")
  (delete-chat-photo True
    {:optional true}
    "Service message: the chat photo was deleted.")
  (group-chat-created True
    {:optional true}
    "Service message: the group has been created.")
  (supergroup-chat-created True
    {:optional true}
    "Service message: the supergroup has been created.")
  (channel-chat-created True
    {:optional true}
    "Service message: the channel has been created.")
  (migrate-to-chat-id Integer
    {:optional true}
    "The group has been migrated to a supergroup with the specified identifier,
    not exceeding 1e13 by absolute value.")
  (migrate-from-chat-id Integer
    {:optional true}
    "The supergroup has been migrated from a group with the specified
    identifier, not exceeding 1e13 by absolute value.")
  (pinned-message Message
    {:optional true}
    "Specified message was pinned. Note that the Message object in this field
    will not contain further reply_to_message fields even if it is itself a
    reply."))


(tapi-type MessageEntity
  "This object represents one special entity in a text message. For example,
  hashtags, usernames, URLs, etc."
  (type String
    {}
    "Type of the entity. One of mention (@username), hashtag, bot_command, url,
    email, bold (bold text), italic (italic text), code (monowidth string),
    pre (monowidth block), text_link (for clickable text URLs).")
  (offset Integer
    {}
    "Offset in UTF-16 code units to the start of the entity.")
  (length Integer
    {}
    "Length of the entity in UTF-16 code units.")
  (url String
    {:optional true}
    "For “text_link” only, url that will be opened after user taps on the
    text."))


(tapi-type PhotoSize
  "This object represents one size of a photo or a file / sticker thumbnail."
  (id String
    {:original-name file_id}
    "Unique identifier for this file.")
  (width Integer
    {}
    "Photo width.")
  (height Integer
    {}
    "Photo height.")
  (file-size Integer
    {:optional true}
    "File size."))


(tapi-type Audio
  "This object represents an audio file to be treated as music by the Telegram
  clients."
  (id String
    {:original-name file_id}
    "Unique identifier for this file.")
  (duration Integer
    {}
    "Duration of the audio in seconds as defined by sender.")
  (performer String
    {:optional true}
    "Performer of the audio as defined by sender or by audio tags.")
  (title String
    {:optional true}
    "Title of the audio as defined by sender or by audio tags.")
  (mime-type String
    {:optional true}
    "MIME type of the file as defined by sender.")
  (file-size Integer
    {:optional true}
    "File size."))


(tapi-type Document
  "This object represents a general file (as opposed to photos, voice messages
  and audio files)."
  (id String
    {:original-name file_id}
    "Unique file identifier.")
  (thumb PhotoSize
    {:optional true}
    "Document thumbnail as defined by sender.")
  (file-name String
    {:optional true}
    "Original filename as defined by sender.")
  (mime-type String
    {:optional true}
    "MIME type of the file as defined by sender.")
  (file-size Integer
    {:optional true}
    "File size."))
