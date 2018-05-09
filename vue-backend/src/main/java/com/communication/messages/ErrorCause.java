package com.communication.messages;

public class ErrorCause {
    
    private ErrorCode error;
    private String errorMessage;
    
    public ErrorCause(ErrorCode error) {
        this.error = error;
        this.errorMessage = error.name();
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ErrorCode getErrorCode() {
        return this.error;
    }

    @Override
    public String toString() {
        return this.errorMessage + " (" + this.error.toString() + ")";
    }
}
