export default class ResponseMessage {
    private p_success: boolean;
    //May change to a specific type of causes
    private p_cause: any;


    public get success() : boolean {
        return this.p_success;
    }

    public get cause() : any {
        return this.p_cause
    }
}