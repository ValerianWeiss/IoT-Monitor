export default class RegisterRequest {
    
    private username: string;
    private password: string;
    private passwordRepeated: string;
    private email: string;

    constructor(username: string, password: string,
                    passwordRepeated: string, email: string) {
        this.username = username;
        this.password = password;
        this.passwordRepeated = passwordRepeated;
        this.email = email;
    }
}