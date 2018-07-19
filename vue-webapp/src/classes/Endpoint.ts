export default class Endpoint {
    
    private name: string;
    private description: string;
    private token: string;

    constructor(name: string, description: string, token: string) {
        this.name = name;
        this.description = description;
        this.token = token;
    }

    public getName() : string {
        return this.name;
    }

    public getDescription() : string {
        return this.description;
    }
}