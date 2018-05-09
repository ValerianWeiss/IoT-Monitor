export default class LoginRequest {
    private username: string;
    private password: string;

    constructor(username: string, password: string) {
        this.password = password;
        this.username = username;
    }
}