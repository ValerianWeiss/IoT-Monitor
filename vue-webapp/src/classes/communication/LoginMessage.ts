export default class LoginMessage {
    private p_username: string;
    private p_password: string;

    constructor(username: string, password: string) {
        this.p_password = password;
        this.p_username = username;
    }
}