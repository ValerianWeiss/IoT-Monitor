import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Client, Message, Frame } from 'stompjs';
import User from './User';

export default class WebSocket {

    private client: Client;

    public constructor() {
        let client = Stomp.over(new SockJS('http://localhost:8090/vueAppWebSock'));
        client.connect({}, function(frame?: Frame) : void {
            client.subscribe('/topic/hello', function(message: Message) : void {
                console.log("got message" + message);
            });
        }, 
        function(message?: string) : void {
            console.log('Error occurred (WebSocket): ' + message);
        });

        this.client = client;
    }

    public send(object: any, path: string) : void {
        this.client.send('/vueapp/' + path, {}, JSON.stringify(object));
    }
}

