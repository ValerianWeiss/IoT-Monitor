<template>
	<div class="graphView">
        <div class="graphBox">
            <h2>{{heading}}</h2>
            <div id="graphWrapper">
                <canvas id="graph"/>
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

@Component
export default class GraphView extends Vue {
    
    private heading: string;
    private name: string;
    private chartConfig: any; 
    
    public constructor() {
        super();
        this.heading = String.Empty;
        this.chartConfig = ChartConfig;
    }

    public setHeading(heading: string) : void {
        this.heading = heading;
    }

    public addDataEndpoint(topic: string){
        this.$store.commit('subscribe', {topic:topic, callback: () => {
            console.log(this.name + " is drawing a datapoint");
        }});
    }
            
    private mounted () {        
        this.$emit('graphViewMounted', this);
        var canvas = <HTMLCanvasElement> document.getElementById('graph');
        var ctx = canvas.getContext("2d");
        let graph: Chart;
        if(ctx != null) {
            graph = new Chart(ctx, this.chartConfig);
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
</style>
