import ErrorCause from './Error';

export default class ResponseMessage {
    
    public success: boolean;
    public payload?: any;
    //May change to a specific type of causes
    public errorCause?: ErrorCause;

}