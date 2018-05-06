package com.messages;

public class MessageSayHello {
    
    private String message;


    public MessageSayHello(String message) {
        this.message = message;
    }
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}