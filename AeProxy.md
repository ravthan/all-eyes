# AeProxy #

The aeProxy is a multi-threaded application that acts as the proxy between the aeManager Android application and the 'ae' daemon.  Its purpose is to cache the ae message events until the user is able to receive and act upon the events.  To accomplish this the aeProxy constructs a single SSL connection (mutual authenticated) to 'ae' daemon process and the following threads:
  * The `AeHeartbeat` thread every thirty seconds sends a heartbeat message to the 'ae' daemon. The 'ae' daemon on receipt of the heartbeat will respond with the latest status of all the monitors.  However the `AeHeartbeat` thread does not receive the status message as that is the job of the `AeStatusKeeper` thread.
  * The `AeStatusKeeper` thread receives the response to the heartbeat from the 'ae' daemon. On receipt validates each field of the message and valid messages are cached in the aeMessageStore.
  * The `AeMessageStore` thread manages the message store.  The message store is an in-memory hashmap that stores the messages sent to the proxy by the 'ae' daemon as well as statistics on these messages.  The statistics include the date/time the event message was first reported,  the date/time the message was last reported, and a count of how many times the message was reported.  The statistics assist in the management of this ethereal message store. The monitors will report the message events roughly every thirty seconds.  If no message updates are received within a two minute window the message is considered to have expired or have been corrected by action response from the aeManager.  Once expired the `AeMessageStore` removes the message and its statistics from the message store.
  * The `AeSSLServer` thread manages connections from the aeManager Android application.  When an SSL connection is accepted the AeSSLServer send a snapshot of the event messages that reside in the `AeMessageStore`.  In response the aeManager will respond with any action messages that the user has sent.

AE Proxy uses SSL for communicating with ae daemon.  One has to create SSL keys for this purpose.  The details of creating keys are at: http://code.google.com/p/all-eyes/wiki/KeyManagementTool