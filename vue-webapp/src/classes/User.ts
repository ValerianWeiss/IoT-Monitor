export default class User {

    //a UUID which is getting retrieved from the server application
    private username?: string;
    
    public constructor(username?: string) {
        this.username = username;
    }
    
    public getUsername() : string | undefined {
        return this.username;
    }
}