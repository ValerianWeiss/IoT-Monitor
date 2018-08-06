let axios = require('Axios');

let datapoint = 0;

setInterval(addDatapoint, 2000);

function addDatapoint() {
    for(let i = 0; i < 6; i++) {
        if(Math.random() > 0.5) {
            datapoint = datapoint + Math.random()*6-2;
        axios.post('http://localhost:8090/data', {
        endpointName: 'Test2',
        sensorName: 'Sensor' + i,
        datapoint: {
            value: datapoint,
            time: new Date().getTime(),
            topic: ''
        }
        },
        {
            headers : { Authorization : "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzMyMjA1MTgsImRldmljZU5hbWUiOiJkYXNkYXNkYXNkIiwidXNlcm5hbWUiOiJhc2Rhc2QifQ.L_ywa2BlVR5CnASy61ZMzadgEi5G6uSK93jNUg_SvL4" }
        });
        }
    }
}