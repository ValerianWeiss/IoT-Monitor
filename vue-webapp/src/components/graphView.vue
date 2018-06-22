<template>
	<div class="graphView">
        <div class="graphBox">
            <h2 id="GraphHeading">{{heading}}</h2>
            <div id="graphWrapper">
                <canvas id="graph" ref="canvas"/>
            </div>
        </div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import ChartConfig from '../classes/ChartConfig';
import { String } from 'typescript-string-operations';
import { Chart } from 'chart.js';
import DataPoint from '../classes/DataPoint';
import { Frame } from 'stompjs';

@Component
export default class GraphView extends Vue {
    
    private heading: string;
    private name: string;
    private chartConfig: any; 
    private graph: Chart;
    private deltaTime:number;


    public constructor() {
        super();
        this.heading = String.Empty;
        this.name = String.Empty;
        this.chartConfig = new ChartConfig().chartConfing;
    }

    public setHeading(heading: string) : void {
        this.heading = heading;
    }

    public setName(name: string) : void {
        this.name = name;
    }

    public getName() : string {
        return this.name;
    }

    public addDataEndpoint(topic: string) : void {
        this.deltaTime = Date.now()
        this.$store.commit('subscribe', {topic:topic, callback: (frame?: Frame) => {
                if(frame != undefined) {
                    let dataPoint: DataPoint = new DataPoint();
                    Object.assign(dataPoint, JSON.parse(frame.body));
                    this.drawNewDataPoint(dataPoint.getValue());
                }
        }});
    }

    private drawNewDataPoint(value: number) : void {
        let labels = this.chartConfig.data.labels;
        console.log(this.chartConfig.data.labels)
        labels.shift();
        labels.push(((Date.now() - this.deltaTime)/1000).toFixed(2));

        this.chartConfig.data.datasets.forEach((dataset: any) => {
            if(dataset.data.length >= 10) {  
                dataset.data.shift();
            }

            dataset.data.push(value);
        });
        this.graph.update();
    }
            
    private mounted () {        
        this.$emit('graphViewMounted', this);
        let t = this.$refs.canvas;
        let canvas = <HTMLCanvasElement> this.$refs.canvas;
        var ctx = canvas.getContext("2d");
        if(ctx != null) {
            this.graph = new Chart(ctx, this.chartConfig);
        }        
    }
}
</script>

<style scoped>
.graphBox {
    float: left;
    border: 1px;
    border-style: solid;
    border-color: black;
    height: 40vh;
    width: calc(50% - 2px);
    background-color: white;
}

#graph {
    position: relative;
    height: 280px;
    width: 100%;
    background-color: white;
}

#GraphHeading {
    margin-left: 30px;
}
</style>
