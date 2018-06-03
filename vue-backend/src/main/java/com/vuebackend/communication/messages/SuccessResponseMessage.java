package com.vuebackend.communication.messages;

public class SuccessResponseMessage extends ResponseMessage {
    
    private Object payload;

    public SuccessResponseMessage() {
        super(true);
    }

    public SuccessResponseMessage(Object payload) {
        super(true);
        this.payload = payload;
    }

	public Object getPayload() {
		return payload;
	}
}