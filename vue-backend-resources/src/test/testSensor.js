let axios = require('Axios');

let datapoint = 0;

setInterval(addDatapoint, 2000);

function addDatapoint() {
    for(let i = 0; i < 6; i++) {
        if(Math.random() > 0.5) {
            datapoint = datapoint + Math.random()*6-2;
        axios.post('http://localhost:8090/data', {
        username: 'asdasd',
        endpointName: 'Test',
        sensorName: 'Sensor' + i,
        datapoint: {
            value: datapoint,
            time: new Date().getTime(),
            topic: ''
        }
        },
        {
            headers : { Authorization : "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzI0MjgxMDUsImRldmljZU5hbWUiOiJFbmRwb2ludCIsInVzZXJuYW1lIjoiYXNkYXNkIn0.d3RB8nLQ9UzhWEOLH4hUnpn2voLOOIlcFi9gXTNS30c" }
        });
        }
    }
}