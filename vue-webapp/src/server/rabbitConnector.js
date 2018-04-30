var server = require('express')();
var http = require('http').Server(server);
var SockIo = require('socket.io')(http);
var rabbitMq = require('amqp').createConnection({ host: 'localhost:5672' });

rabbitMq.on('ready', function () {
   SockIo.sockets.on('connection', function (socket) {
        var queue = rabbitMq.queue('my-queue');

        queue.bind('#'); // all messages

        queue.subscribe(function (message) {
            socket.emit('message-name', message);
        });
    });
});

server.listen(8800);
