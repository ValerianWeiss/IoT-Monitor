package com.communication.messages;

public abstract class ResponseMessage {
    
    private boolean success;

    
    public ResponseMessage(boolean requestWasSuccessful) {
        this.success = requestWasSuccessful;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return this.success;
    }
}