import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Client, Message, Frame } from 'stompjs';
import Config from '../appConfig.json';

export default class WebSocket {

    private client: Client;

    public constructor() {
        let client = Stomp.over(new SockJS(Config.websocketUrl));
        client.connect({}, (frame?: Frame) : void => {
            client.subscribe(Config.websocketTopicRoot + '/hello', function(message: Message) : void {
                console.log("got message" + message);
            });
        }, 
        function(message?: string) : void {
            console.log('Error occurred (WebSocket): ' + message);
        });

        this.client = client;
    }
    
    public subscribe(topic: string, callback: any) : void {
        this.client.subscribe(topic, callback);
        console.log("subsribed to topic" + topic);
        
    }

    public send(object: any, path: string) : void {
        this.client.send(Config.websocketSendPrefix + path, {}, JSON.stringify(object));
    }
}

