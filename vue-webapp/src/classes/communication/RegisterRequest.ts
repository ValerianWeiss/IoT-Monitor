export default class RegisterRequest {
    
    private username: string;
    private password: string;
    private passwordRepeated: string;

    constructor(username: string, password: string,
                    passwordRepeated: string) {
        this.username = username;
        this.password = password;
        this.passwordRepeated = passwordRepeated;
    }
}