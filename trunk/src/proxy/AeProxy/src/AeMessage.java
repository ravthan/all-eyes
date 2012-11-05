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
 * Original Author: Thomas Pari
 */


package org.tbrt.ae;

import java.util.ArrayList;

public class AeMessage {
    
    private String versionNumber;  // the message version number
    private String messageType;    // the message type
    private String monitorName;    // the monitor that send the message
    private String eventName;      // the event that occurred
    private String statusCode;     // the status code
    private String actionList;     // the list of possible actions to take
    private String messageText;    // the text associated with the message 

    //=======================================================================
    // The AE Message constructor
    //=======================================================================
    public AeMessage() {
        resetValues();
    }
    
    //=======================================================================
    // The resetValues sets all the member variables to their init state
    //=======================================================================
    private void resetValues() {
        versionNumber = "10";  // Version 1.0
        messageType   = "";    // Set the message type as unpopulated
        monitorName   = "";    // Set the component name as unpopulated
        eventName     = "";    // Set the event name as unpopulated
        statusCode    = "";    // Set the status code as unpopulated
        actionList    = "";    // Set the action list as unpopulated
        messageText   = "";    // Set the message text as unpopulated
    }
    
    //=======================================================================
    // The validateString method checks to see if the specified string
    // is non-null and non-empty.  If the values matrix is supplied it 
    // also checks to see if the input string is a valid value.
    //=======================================================================
    private boolean validateString(String input, String[][] values) {
        if(input == null) {
            // Null value is invalid
            return false;
        }
        
        String tmpstr = input.trim();
        if(tmpstr.equals("")) {
            // Empty strings are invalid
            return false;
        }
        
        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                if(values[i][0].equals(tmpstr)) {
                    // Found a valid value
                    return true;
                }
            }
        }
        else {
            // Valid non-null string
            return true;    
        }
        return false;
    }
    
    //===================( Message Type Methods )============================
    
     private static final String[][] validMessageTypes = {
         { "00", "Heartbeat"},
         { "11", "Acknowledgement"},    
         { "22", "Event"},        
         { "33", "Action"}        
    };   
     
    //
    // Get the message type code for the message
    //
    public String getMessageType() {
        return this.messageType;
    }
    
    //
    // Get the detail message type for the message
    //
    public String getLongMessageType() {
        for(int i = 0; i < validMessageTypes.length; i++) {
            if(validMessageTypes[i][0].equals(this.messageType)) {
                return validMessageTypes[i][1];
            }
        }
        return this.messageType;
    }

    //
    // Set the message type
    //
    public void setMessageType(String messageType) {
        if(validateString(messageType, validMessageTypes)) {
            this.messageType = messageType;
        }
        else {
            this.messageType = "";
        }
        return;
    }
    
    //======================( Monitor Methods )============================
    
    private static final String[][] validMonitors = {
        { "AE", "AE-Daemon"},
        { "AM", "AE-Manager"},    
        { "PM", "Process-Monitor"},        
        { "SM", "AE-Socket-Monitor"},
        { "FM", "AE File Monitor"},    
        { "FD", "AE File Descriptor Monitor"},    
        { "SF", "AE Self Monitor"}        
    };    
    
    //
    // Get the component name code
    //
    public String getMonitorName() {
        return this.monitorName;
    }
    
    //
    // Set the detailed component name
    //
    public String getLongMonitorName() {
        for(int i = 0; i < validMonitors.length; i++) {
            if(validMonitors[i][0].equals(this.monitorName)) {
                return validMonitors[i][1];
            }
        }
        return this.monitorName;
    }

    //
    // Set the component name
    //
    public void setMonitorName(String monitorName) {
        if(validateString(monitorName, validMonitors)) {
            this.monitorName = monitorName;
        }
        else {
            this.monitorName = "";
        }
        return;
    }
    
    //======================( Event Name Methods )=======================

    private static final String[][] validEvents = {
        { "0001", "Blacklisted port in use"},
        { "0002", "Whitelisted port not found"},    
        { "0003", "Critical process not running"},        
        { "0004", "Detected change in critical file"},
        { "0005", "Watched file accessed"},    
        { "9999", "Initial Channel validation event"}        
    };        
    
    //
    // Get the event name
    //
    public String getEventName() {
        return this.eventName;
    }
    
    //
    // Get the long event name
    //
    public String getLongEventName() {
        for(int i = 0; i < validEvents.length; i++) {
            if(validEvents[i][0].equals(this.eventName)) {
                return validEvents[i][1];
            }
        }
        return this.eventName;
    }

    //
    // Set the event name
    //
    public void setEventName(String eventName) {
        if(validateString(eventName, validEvents)) {
            this.eventName = eventName;
        }
        else {
            this.eventName = "";
        }
        return;
    }

    //======================( Status Code Methods )=======================
    
    private static final String[][] validStatusCodes = {
        { "00", "Reporting all is well"},
        { "10", "Previously reported event cleared"},
        { "01", "Failed to check event"},    
        { "11", "Reporting failure event"}        
    };
    
    //
    // Get the status code
    //
    public String getStatusCode() {
        return this.statusCode;
    }
    
    //
    // Get the long description of the status code
    //
    public String getLongStatusCode() {
        for(int i = 0; i < validStatusCodes.length; i++) {
            if(validStatusCodes[i][0].equals(this.statusCode)) {
                return validStatusCodes[i][1];
            }
        }
        return this.statusCode;
    }

    //
    // Set the status code
    //
    public void setStatusCode(String statusCode) {
        if(validateString(statusCode, validStatusCodes)) {
            this.statusCode = statusCode;
        }
        else {
            this.statusCode = "";
        }
        return;
    }
    
    //======================( Action List Methods )=======================

    private static final String[][] actionBits = {
        { "A0", "Do nothing"},
        { "A1", "Reboot"}
        };
    
    //
    // Get the actions list
    //
    public String getActionList() {
        return actionList;
    }
    
    //
    // Get the long descriptions of the actions
    //
    public ArrayList<String> getLongActionList() {
        if(actionList == null || actionList.equals("")) {
            return null;
        }
        
        ArrayList<String> list = new ArrayList<String>();
        String[] actionNums = actionList.split("A", actionBits.length+2);
        boolean firsttime = true;
        for(String actionNum : actionNums) {
            // Do to parsing these odd action strings the first split
            // will result in a empty string
            if(actionNum == null || actionNum.equals("")) {
                if(firsttime) {
                    firsttime = false;
                    continue;
                }
                else {
                    System.out.println("[ERROR] Parsing error -  invalid action");
                    return null;
                }
            }
            firsttime = false;

            // Verify the action is valid
            boolean found = false;
            for(int i = 0; i < actionBits.length; i++) {
                if(actionBits[i][0].equals("A"+actionNum)) {
                    list.add(actionBits[i][1]);
                    found = true;
                    break;
                }
            }

            // If not found the action is invalid
            if(!found) {
                System.out.println("[ERROR] Parsing error -  invalid action");
                return null;
            }
        }
        return list;
    }

    //
    // Set the actions
    //
    public void setActionList(String actionList) {
        if(actionList == null || actionList.equals("")) {
            System.out.println("[ERROR] Attempting to set an empty action");
            this.actionList = "";
            return;
        }

        ArrayList<String> list = new ArrayList<String>();
        String[] actionNums = actionList.split("A", actionBits.length+2);
        boolean firsttime = true;
        for(String actionNum : actionNums) {
            // Do to parsing these odd action strings the first split
            // will result in a empty string
            if(actionNum == null || actionNum.equals("")) {
                if(firsttime) {
                    firsttime = false;
                    continue;
                }
                else {
                    System.out.println("[ERROR] Attempting to set an invalid action [" + actionList + "]");
                    this.actionList = "";
                    return;
                }
            }
            firsttime = false;

            // Verify the action is valid
            boolean found = false;
            for(int i = 0; i < actionBits.length; i++) {
                if(actionBits[i][0].equals("A"+actionNum)) {
                    found = true;
                }
            }

            if(!found) {
                System.out.println("[ERROR] Attempting to set an invalid action [" + actionList + "]");
                this.actionList = "";
                return;
            }
        }

        this.actionList = actionList;
        return;
    }
    
    //======================( Message Text Methods )=======================

    //
    // Get the message text
    //
    public String getMessageText() {
        return this.messageText;
    }

    //
    // Set the message text
    //
    public void setMessageText(String messageText) {
        if(messageText == null) {
            this.messageType = "";
        }
        else if(messageText.length() > 79) {
            this.messageText = messageText.substring(0, 79);
        }
        else {
            this.messageText = messageText;
        }
    }
    
    //-----------------------------------------------------------------------

    //========================================================================
    // The isValid method checks the constructed message to see if it is valid
    //========================================================================
    public boolean isValid() {
         // If the getting the long representation return the empty string its invalid
        String t1 = this.getLongMessageType();
        if(t1.equals("")) {
            System.out.println("[INFO] Message type is invalid");
            return false;
        }
        
        // If the getting the long representation return the empty string its invalid
        t1 = this.getLongMonitorName();
        if(t1.equals("")) {
            System.out.println("[INFO] Monitor name is invalid");
            return false;
        }
        
        if (messageType.equals("00") ||
            messageType.equals("11")) {
           return true;
        }
        
        // If the getting the long representation return the empty string its invalid
        t1 = this.getLongEventName();
        if(t1.equals("")) {
            System.out.println("[INFO] Event name is invalid");
            return false;
        }
        
        // If the getting the long representation return the empty string its invalid
        t1 = this.getLongStatusCode();
        if(t1.equals("")) {
            System.out.println("[INFO] Status code is invalid");
            return false;
        }
        
        // The action list is am array of 0's and 1's with a fixed length
        t1 = this.getActionList();
        if(t1 == null) {
            System.out.println("[INFO] Action list is invalid");
            return false;
        }
        
        // In an event message
        // the number of actions specified does not matter in the event
        // the user will select one of the specified actions
        if (messageType.equals("22")) {
            return true;
        }
        
        // In an action message
        // the number of actions specified should be 1 or less
        if(messageType.equals("33")) {
            ArrayList actions = this.getLongActionList();
            if(actions.size() < 1) {
                System.out.println("[INFO] Action list is invalid");
                return false;
            }
            else {
                return true;
            }
        }
        
        return false;
    }
    
    //========================================================================
    // Get the string representation of the message
    //========================================================================
    public String toString() {
        
        if(!isValid()) {
            return ("");
        }
        
        // Heartbeat message
        if(messageType.equals("00")) {
            return new String(
                    "[:" + this.versionNumber +
                    ":" + this.messageType + 
                    ":" + this.monitorName +
                    ":]");
        }
        // ACK message
        else if(messageType.equals("11")) {
            return new String(
                    "[:" + this.versionNumber +
                    ":" + this.messageType + 
                    ":" + this.monitorName +
                    ":]");
        }
        // Event message
        else if(messageType.equals("22")) {
            return new String(
                    "[:" + this.versionNumber +
                    ":" + this.messageType + 
                    ":" + this.monitorName +
                    ":" + this.eventName +
                    ":" + this.statusCode +
                    ":" + this.actionList +
                    ":" + this.messageText +
                    ":]");
        }
        // Action message
        else if(messageType.equals("33")) {
            return new String(
                    "[:" + this.versionNumber +
                    ":" + this.messageType + 
                    ":" + this.monitorName +
                    ":" + this.eventName +
                    ":" + this.statusCode +
                    ":" + this.actionList +
                    ":" + this.messageText +
                    ":]");
        }
        return new String("");
    }
    
    //========================================================================
    // Parse of message and construct an instamce of the AeMessage
    //========================================================================
    public static AeMessage parse(String rawMessage) {
        if(rawMessage == null) {
            return null;
        }
        
        // split the message up into chunks
        String[] parts = rawMessage.split(":", 9);
        
        // create the message that might be returned to the user
        AeMessage ae = new AeMessage();
        
        // The following keep state as we parse the messages:
        boolean findBegin       = true;
        boolean findVersion     = false;
        boolean findMesgType    = false;
        boolean findMonitorName = false;
        boolean findEventId     = false;    
        boolean findStatusCode  = false;
        boolean findActionList  = false;
        boolean findText        = false;
        boolean findEnd         = false;

        // Parse the message
        for(int idx = 0; idx < parts.length; idx++) {
            if(findBegin) {
                if(parts[idx].trim().equals("[")) {
                    findVersion = true;
                    findBegin = false;
                    continue;
                }
                else {
                    System.out.println("[ERROR] Begin message marker not found");
                    return null;
                }
            }
            else if(findVersion) {
                if(parts[idx].equals("10")) {
                    findVersion = false;
                    findMesgType = true;
                    continue;
                }
                else {
                    System.out.println("[ERROR] Invalid version");
                    return null;
                }
            }
            else if(findMesgType) {
                if(parts[idx].equals("00") ||
                    parts[idx].equals("11") ||
                    parts[idx].equals("22") ||
                    parts[idx].equals("33")) {
                    findMesgType = false;
                    findMonitorName = true;
                    ae.setMessageType(parts[idx]);
                    continue;
                }
                else {
                    System.out.println("[ERROR] invalid message type");
                    return null;
                }
            }
            else if(findMonitorName) {
                if(parts[idx].equals("AE") ||
                   parts[idx].equals("SM") ||
                   parts[idx].equals("PM") ||
                   parts[idx].equals("FM") ||
                   parts[idx].equals("FD") ||
                   parts[idx].equals("SF") ||
                   parts[idx].equals("AM")) {
                    findMonitorName = false;
                    ae.setMonitorName(parts[idx]);
                    String type = ae.getMessageType();
                    if(type.equals("00")) {       // Heartbeat message
                        findEnd = true;
                    }
                    else if(type.equals("11")) {  // ACK message
                        findEnd = true;
                    }
                    else if(type.equals("22")) {  // event message
                        findEventId = true;
                    }
                    else if(type.equals("33")) {  // action message
                        findEventId = true; 
                    }
                    else {
                        System.out.println("[ERROR] unexpected message type");
                        return null;
                    }
                    continue;
                }
                else {
                    System.out.println("[ERROR] invalid monitor name");
                    return null;
                }
            }
            else if(findEventId) {
                if(parts[idx].equals("0001") ||
                   parts[idx].equals("0002") ||
                   parts[idx].equals("0003") ||
                   parts[idx].equals("0004") ||
                   parts[idx].equals("0005") ||
                   parts[idx].equals("9999")) {
                    findEventId = false;
                    ae.setEventName(parts[idx]);
                    String type = ae.getMessageType();
                    if(type.equals("22")) {  // event message
                        findStatusCode = true;
                    }
                    else if(type.equals("33")) {  // action message
                        findStatusCode = true;
                    }
                    else {
                        System.out.println("[ERROR] unexpected message type");
                        return null;
                    }
                    continue;
                }
                else {
                    System.out.println("[ERROR] invalid event ids");
                    return null;
                }
            }
            else if(findStatusCode) {
                if(parts[idx].equals("00") ||
                   parts[idx].equals("01") ||
                   parts[idx].equals("11")) {
                    findStatusCode = false;
                    ae.setStatusCode(parts[idx]);
                    String type = ae.getMessageType();
                    if(type.equals("22")) {  // event message
                        findActionList = true;
                    }
                    else if(type.equals("33")) {  // action message
                        findActionList = true;
                    }
                    else {
                        System.out.println("[ERROR] unexpected message type");
                        return null;
                    }
                    continue;
                }
                else {
                    System.out.println("[ERROR] invalid status code");
                    return null;
                }
            }
            else if(findActionList) {
                if(parts[idx] == null || parts[idx].equals("")) {
                    System.out.println("[ERROR] invalid empty actions");
                    return null;
                }
                else {
                    ae.setActionList(parts[idx]);
                    String checkstr = ae.getActionList();
                    if(checkstr.equals("")) {
                        System.out.println("[ERROR] invalid actions");
                        return null;
                    }
                    findActionList = false;
                    findText = true;
                    continue;
                }
            }
            else if(findText) {
                if(parts[idx].length() > 0) {
                    findText = false;
                    ae.setMessageText(parts[idx]);
                    String type = ae.getMessageType();
                    if(type.equals("22")) {  // event message
                        findEnd = true;
                    }
                    else if(type.equals("33")) {  // action message
                        findEnd = true;
                    }
                    else {
                        System.out.println("[ERROR] unexpected message type");
                        return null;
                    }
                    continue;
                }
                else {
                    System.out.println("[ERROR] invalid text");
                    return null;
                }
            }
            else if(findEnd) {
                if(parts[idx].trim().equals("]")) {
                    findEnd = false;
                    continue;
                }
                else {
                    System.out.println("[ERROR] End of message marker not found");
                    return null;
                }
            }
            else {
                System.out.println("[ERROR] Unexpected part");
                return null;
            }
        }
        
        if(ae.isValid()) {
            return ae;
        }
        return null;
    }
    
    //========================================================================
    // The main method for testing purposes
    //========================================================================
    public static void main(String[] args) {
        // Test Empty message
        String tm = "";
        AeMessage msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        }
        System.out.println("--------------------");
        
        // Test wrong version
        tm = "[:11:00:AM:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        }
        System.out.println("--------------------");
        
        // Test heartbeat
        tm = "[:10:00:AM:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test ACK
        tm = "[:10:11:AM:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Event message
        tm = "[:10:22:SM:0001:11:A0:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #1
        tm = "[:10:33:SM:0001:11:A0A1:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #2
        tm = "[:10:33:SM:0001:11:A0:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #3
        tm = "[:10:33:SM:0001:11:A1:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #4
        tm = "[:10:33:SM:0001:11:A0A1A2:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #5
        tm = "[:10:33:SM:0001:11:A0A1A:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test Action #6
        tm = "[:10:33:SM:0001:11:AAAAAA:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        
        // Test Action #6
        tm = "[:10:33:SM:0001:11::8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        
        // Test Action #6
        tm = "[:10:33:SM:0001:11:BBBB:8080:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test text field #1
        tm = "[:10:33:SM:0001:11:A0A1::]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        
        // Test text field #2
        tm = "[:10:33:SM:0001:11:A0A1: :]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        
        // Test text field #2
        tm = "[:10:33:SM:0001:11:A0A1:x:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test text field #3
        tm = "[:10:33:SM:0001:11:A0A1:zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        // Test text field #4
        tm = "[:10:33:SM:0001:11:A0A1:zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");
        
        
        // Test text field #4
        tm = "[:10:33:SM:0001:11:A0A1:zzzzzzzzzzzzzzzzzzzzzzzzzzxxxxxxxxxxxxxxzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz:]";
        msg = AeMessage.parse(tm);
        if(msg != null && msg.isValid()) {
            System.out.println("BFORE>" + tm + "<");
            System.out.println("AFTER>" + msg.toString() + "<");  
        }
        else {
            System.out.println("MESSAGE IS INVALID");
        } 
        System.out.println("--------------------");

        msg = new AeMessage();
        msg.setActionList("A0");
        System.out.println("A0=" + msg.getActionList());
        System.out.println("A0=" + msg.getLongActionList());
        msg.setActionList("A1");
        System.out.println("A1=" + msg.getActionList());
        System.out.println("A1=" + msg.getLongActionList());
        msg.setActionList("A0A1");
        System.out.println("A0A1=" + msg.getActionList());
        System.out.println("A0A1=" + msg.getLongActionList());
        msg.setActionList("A0A1A2");
        msg.setActionList("AAA1A2");
        msg.setActionList("BBBBB2");
        msg.setActionList("");
        
    }
}