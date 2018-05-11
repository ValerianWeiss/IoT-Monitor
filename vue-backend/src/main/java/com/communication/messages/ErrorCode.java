package com.communication.messages;

public enum ErrorCode {
    usernameIncorrect(101),
    usernameAlreadyTaken(102),
    emailAlreadyTaken(103),
    passwordIncorrect(104),
    passwordsEqual(105);

    private int numVal;

    private ErrorCode(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}