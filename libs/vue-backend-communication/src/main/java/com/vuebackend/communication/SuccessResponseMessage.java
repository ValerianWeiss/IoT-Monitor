package com.vuebackend.communication;

public class SuccessResponseMessage <T> extends ResponseMessage {
    
    private T payload;

	
    public SuccessResponseMessage() {
        super(true);
    }

    public SuccessResponseMessage(T payload) {
        super(true);
        this.payload = payload;
    }

	public T getPayload() {
		return payload;
	}
}