import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Client } from 'stompjs';

export default class RabbitmqSocket {

    private client: Client;

    constructor() {
        Stomp.over(new SockJS("http://127.0.0.1:15670/ws"));
        this.client.connect('guest', 'guest', this.onConnect, this.onError, "vhost");
        this.client.heartbeat.outgoing = 0;
        this.client.heartbeat.incoming = 0;
    }

    onConnect() {
        console.log("connected to Rabbitmq");
    }

    onError() {
        console.log("Error in Rabbitmq");
    }
}
