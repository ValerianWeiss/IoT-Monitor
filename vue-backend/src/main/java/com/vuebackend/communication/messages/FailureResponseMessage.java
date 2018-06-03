package com.vuebackend.communication.messages;

public class FailureResponseMessage extends ResponseMessage {
    
    private ErrorCause error;

    public FailureResponseMessage(ErrorCause error) {
        super(false);
        this.error = error;
    }

    public ErrorCause getErrorCause() {
        return this.error;
    }
}