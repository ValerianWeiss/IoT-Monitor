export default class User {

    //a UUID which is getting retrieved from the server application
    private userToken?: string;
    private username?: string;
    private password?: string;
    
    constructor(userToken?: string, username?: string, password?:string) {
        this.userToken = userToken;
        this.username = username;
        this.password = password;
    }
    
    public getUserToken() : string | undefined {
        return this.userToken;
    }

    public setUserToken(token: string | undefined) {
        if(token == undefined) {
            throw new TypeError("tried to set usertoken to undefined");
        }
        this.userToken = token;
    }
}