export default class User {

    //a UUID which is getting retrieved from the server application
    private _userToken?: string;
    private _username?: string;
    private _password?: string;
    
    constructor(userToken?: string, username?: string, password?:string) {
        this._userToken = userToken;
        this._username = username;
        this._password = password;
    }
    
    public get userToken() : string | undefined {
        return this._userToken;
    }

    public set userToken(token: string | undefined) {
        if(token == undefined) {
            throw new TypeError("tried to set usertoken to undefined");
        }
        this._userToken = token;
    }
}