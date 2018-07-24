export default class Datapoint {
    
    private value: number;
    private time: number;
    

    public constructor(value?: number) {
        if(value != null) {
            this.value = value;
        }
    }

    public getValue() : number {
        return this.value;
    }

    public getTime() : number {
        return this.time;
    }
}