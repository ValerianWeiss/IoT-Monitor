package com.vuebackend.communication;

public class ErrorCause {
    
    private int error;
    private String errorMessage;
    
    public ErrorCause(ErrorCode error) {
        this.error = error.getNumVal();
        this.errorMessage = error.name();
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getErrorCode() {
        return this.error;
    }

    @Override
    public String toString() {
        return this.errorMessage + " (" + this.error + ")";
    }
}
