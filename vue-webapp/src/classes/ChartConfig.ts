export default class ChartConfing {
    chartConfing = {
        type: 'line',
        data: {
            labels: ['0', '0', '0', '0', '0', '0','0','0','0','0',
                     '0', '0', '0', '0', '0', '0','0','0','0','0'],
            datasets: [{
                label: '',
                backgroundColor: '#72D9B4',
                borderColor: '#72D9B4',
                data: [0,0,0,0,0,0,0,0,0,0,
                       0,0,0,0,0,0,0,0,0,0],
                fill: true,
            }]
        },
        options: {
            responsive: true,
            showTooltips: false,
            legend: {
                display: false
            },
            title: {
                display: false,
                text: '',
            },
            tooltips: {
                enabled: false,
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'null',
                intersect: false
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Time'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Value'
                    }
                }]
            }
        }
    };
}