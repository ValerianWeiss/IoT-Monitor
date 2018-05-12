export default class User {

    //a UUID which is getting retrieved from the server application
    private sessionToken?: string;
    private username?: string;
    
    public constructor(username?: string, userToken?: string) {
        this.sessionToken = userToken;
        this.username = username;
    }
    
    public getUsername() : string | undefined {
        return this.username;
    }

    public getSessionToken() : string | undefined {
        return this.sessionToken;
    }

    public setUserToken(token: string | undefined) {
        if(token == undefined) {
            throw new TypeError("tried to set usertoken to undefined");
        }
        this.sessionToken = token;
    }
}