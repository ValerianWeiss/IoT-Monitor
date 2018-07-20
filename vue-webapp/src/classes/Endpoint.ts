export default class Endpoint {
    
    private name: string;
    private description: string;
    private token: string;
    private sensors: any[];


    constructor(name: string, description: string, token: string) {
        this.name = name;
        this.description = description;
        this.token = token;
        this.sensors = [];
    }

    public getName() : string {
       return this.name;
    }

    public getDescription() : string {
        return this.description;
    }
}