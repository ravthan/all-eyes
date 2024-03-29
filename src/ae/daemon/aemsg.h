/*
 * Copyright (C) <2012> <Blair Wolfinger, Ravi Jagannathan, Thomas Pari, Todd Chu>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Original Author: Ravi Jagannathan
 */
#ifndef __AEMSG_H__
#define __AEMSG_H__

/*
 * NOE:  All the below defines are derived from 
 * the project wiki page, http://code.google.com/p/all-eyes/wiki/AeMonitorProtocol
 */

/*
 * All Eyes Monitor code names made of 2 letters, as per AeMonitorProtocol (see Wiki).
 */
#define AE_MONCODE_LEN          2        // Monitor code length are 2 characters only.
#define AE_DAEMON               "AE"     // All Eyes, AKA 'AE' Daemon code
#define AE_SELFMON              "SF"     // Self monitor code
#define AE_SOCKETMON            "SM"     // Socket Monitor code
#define AE_PROCMON              "PM"     // Process Monitor code
#define AE_FILEMON              "FL"     // File Monitor code
#define AE_FDMON                "FD"     // File Descriptor Monitor code
#define AE_AEMGR                "AM"     // Android All Eyes Manager code


#define AE_PROTCOL_VER           "10"     // Version number, checked against the msg received
#define AE_MSG_OPEN              "["      // Msg open character
#define AE_MSG_END               "]"      // Msg end character
#define AE_MSG_HEADER            "[:"     // Msg header.  Should it just have '[' and not the ':'?
#define AE_MSG_TRAILER           ":]"     // Msg header.  Should it just have ']' and not the ':'?
#define AE_MSG_DELIMITER         ":"      // Msg delimiter
#define AE_MSG_DELIMITER_CH      ':'      // Char delimiter
#define AE_MONITOR_HELLO         "00"     // Indicates message is a heartbeat message
#define AE_MONITOR_ACK           "11"     // Ack message. Only ae daemon can send this? SECURITY
#define AE_MONITOR_EVENT         "22"     // Indicates the message is of event type
#define AE_MONITOR_ACTION        "33"     // Indicates the message is of action type
#define AE_ACTION_IGNORE         "A0"     // Ignore, do nothing.
#define AE_ACTION_LOG            "A1"     // Log the message.
#define AE_END_OF_RESPONSE       "\n"     // Script based monitors need this.  For C based, it is fine.
#define AE_MSG_ID_LENGTH          32      // Length of Message ID
#define AE_MSG_TYPE_LENGTH        2       // Length of Message ID
#define AE_MON_CODE_LENGTH        2       // Length of Monitor Code ID
#define AE_MON_EVENT_LENGTH       4       // Length of Monitor Event ID
#define AE_MSG_FIELD_LENGTH       256     // Fields of the version, type, monitor code name,
                                          // and message type cannot exceed 255 bytes.
#define AE_MON_STATUS_OPCODE_LENGTH       2       // Length of Monitor Status Opcode
#define AE_MON_ACTION_LENGTH       2       // Length of Monitor Status Opcode

/*
 * Structure that keeps messages after parsing
 * SECURITY: The structure has longer string length than needed.
 * Meant for fast prototyping.
 */
#define AE_AEMSG_FIELD_LENGTH 512
typedef struct asMsg  {
    char header[AE_AEMSG_FIELD_LENGTH];
    char version[AE_AEMSG_FIELD_LENGTH];
    char msgId[AE_AEMSG_FIELD_LENGTH];
    char msgType[AE_AEMSG_FIELD_LENGTH];
    char monCodeName[AE_AEMSG_FIELD_LENGTH];
    char eventId[AE_AEMSG_FIELD_LENGTH];
    char statusOp[AE_AEMSG_FIELD_LENGTH];
    char action[AE_AEMSG_FIELD_LENGTH];
} AEMSG;

extern int chkAeMsgIntegrity (char *msg);
extern int isHeartBeatMsg(AEMSG *msg);
extern int processMsg(char *msg, AEMSG *aeMsg);
extern int replaceMsgType(char *msg, char *newType, char *orgType);
extern void constructMonResponse(AEMSG *aeMsg, char *out);

#endif  // __AEMSG_H__
