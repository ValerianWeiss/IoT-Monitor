import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
var WebSocket = /** @class */ (function () {
    function WebSocket() {
        var client = Stomp.over(new SockJS('http://localhost:8090/vueAppWebSock'));
        client.connect({}, function (frame) {
            client.subscribe('/topic/hello', function (message) {
                console.log("got message" + message);
            });
        }, function (message) {
            console.log('Error occurred (WebSocket): ' + message);
        });
        this.client = client;
    }
    WebSocket.prototype.send = function (object, path) {
        this.client.send('/vueapp/' + path, {}, JSON.stringify(object));
    };
    return WebSocket;
}());
export default WebSocket;
//# sourceMappingURL=WebSocket.js.map