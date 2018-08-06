import Sensor from './Sensor';

export default class Endpoint {
    
    private name: string;
    private description: string;
    private token: string;
    private sensors: Sensor[];


    constructor(name: string, description: string, token: string, sensors?: Sensor[]) {
        this.name = name;
        this.description = description;
        this.token = token;
        
        if(sensors != null) {
            this.sensors = sensors;
        } else {
            this.sensors = [];
        }

    }

    public getName() : string {
       return this.name;
    }

    public getDescription() : string {
        return this.description;
    }

    public getSensors() : Sensor[] {
        return this.sensors;
    }

    public getToken() {
        return this.token;
    }

    public addSensor(sensor: Sensor) : void {
        this.sensors.push(sensor);
    }
}