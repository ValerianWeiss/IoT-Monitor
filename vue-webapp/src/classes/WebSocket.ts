import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Client, Message } from 'stompjs';

export default class WebSocket {

    private client: Client;

    constructor() {
        this.client = Stomp.over(new SockJS("http://localhost:8090/vueAppWebSock"));
        this.client.connect({}, this.onConnect, this.onError);
    }

    private onConnect(frame?: Stomp.Frame) : void {
        console.log('Connected: ' + frame);
        if(this.client == undefined) {
            console.log("client is null");
            
        } else {
            this.client.subscribe("topic/hello", function(message: Message) {
                console.log("got message" + message);
            });
        }
    }

    private onError(message?: string) {
        console.log("Error occurred (WebSocket): " + message);
    }

    private subscribe() {
        
    }
}

