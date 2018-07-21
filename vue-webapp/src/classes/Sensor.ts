export default class Sensor {
    
    private name: string;
    private topic: string;

    constructor(name: string, topic: string) {
        this.name = name;
        this.topic = topic;
    }

    public getName() : string {
       return this.name;
    }

    public getTopic() : string {
        return this.topic;
    }
}