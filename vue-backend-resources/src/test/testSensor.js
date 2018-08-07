let axios = require('Axios');

let datapoint = 0;

setInterval(addDatapoint, 2000);

function addDatapoint() {
    for(let i = 0; i < 6; i++) {
        if(Math.random() > 0.5) {
            datapoint = datapoint + Math.random()*6-2;
        axios.post('http://localhost:8090/data', {
        sensorName: 'Sensor' + i,
        datapoint: {
            value: datapoint,
            time: new Date().getTime()
        }
        },
        {
            headers : { Authorization : "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbmRwb2ludE5hbWUiOiJFbmQiLCJpYXQiOjE1MzM2NzgwNDMsInVzZXJuYW1lIjoiYXNkYXNkIn0.0vaF6Sg4xPaqEPL6gnjJv3I59XgBjFaix8D02qVWDgI" }
        });
        }
    }
}