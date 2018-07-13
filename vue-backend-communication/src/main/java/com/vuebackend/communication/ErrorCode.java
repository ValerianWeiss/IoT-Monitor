package com.vuebackend.communication;

public enum ErrorCode {
    usernameIncorrect(101),
    usernameAlreadyTaken(102),
    emailAlreadyTaken(103),
    passwordIncorrect(104),
    passwordsNotEqual(105),
    notLoggedIn(201),
	unknownError(-1);

    private int numVal;

    private ErrorCode(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}