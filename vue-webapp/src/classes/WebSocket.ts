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
        this.subscriptions.push({
            subscription: subscription,
            topic: topic,
        });
    }

    public send(object: any, path: string) : void {
        this.client.send(Config.websocketSendPrefix + path, {}, JSON.stringify(object));
    }

    public unsubscribe(topic?: string) : void {
        if(topic == null) {
            this.subscriptions.forEach(subscriptionObj => {
                subscriptionObj.subscription.unsubscribe();
            });
        } else {
            this.subscriptions.forEach(subscriptionObj => {
                if(subscriptionObj.topic == topic) {
                    subscriptionObj.subscription.unsubscribe();
                }
            });
        }
    }
}

