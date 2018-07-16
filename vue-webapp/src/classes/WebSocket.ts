import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Client, Frame } from 'stompjs';
import Config from '../appConfig.json';

export default class WebSocket {

    private client: Client;
    private subscriptions: any[];

    public constructor() {
        let client = Stomp.over(new SockJS(Config.websocketUrl));
        client.connect({}, (frame?: Frame) : void => {
            if(frame != null && frame != undefined) {
                console.log(frame.body);
            }
        }, 
        function(message?: string) : void {
            console.error('Error occurred (WebSocket): ' + message);
        });

        this.client = client;
        this.subscriptions = [];
    }
    
    public subscribe(topic: string, callback: any) : void {
        let subscription = this.client.subscribe(Config.websocketTopicRoot + '/' + topic, callback);
        this.subscriptions.push(subscription);
    }

    public send(object: any, path: string) : void {
        this.client.send(Config.websocketSendPrefix + path, {}, JSON.stringify(object));
    }

    public unsubscribe() : void {
        console.log(this.subscriptions.length);
        
        if(this.subscriptions.length > 0) {
            this.subscriptions.forEach(subscription => {
                subscription.unsubscribe();
            });
        }
    }
}

