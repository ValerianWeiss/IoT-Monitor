import Sensor from './Sensor';

export default class Endpoint {
    
    private name: string;
    private description: string;
    private token: string;
    private sensors: Sensor[];


    constructor(name: string, description: string, token: string, sensors: Sensor[]) {
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