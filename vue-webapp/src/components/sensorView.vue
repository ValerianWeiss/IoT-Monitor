<template>
	<div class="sensorView">
        <div id="sensorViewContainer">
            <div id="headerBar" @click="onHeaderClick">
                <h3 id="sensorName">{{sensor.getName()}}</h3>
                <div v-if="!graphVisible" class="showIconContainer">
                    <img class="showIcon" src="../recources/down.png" alt="Show graph">
                </div>
                <div v-if="graphVisible" class="showIconContainer">
                    <img class="showIcon" src="../recources/up.png" alt="Show graph">
                </div>
            </div>

            <div v-show="graphVisible" id="graphContainer">
                <canvas id="graph" ref="canvas"/>
            </div>
        </div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { String } from 'typescript-string-operations';
import { Chart } from 'chart.js';
import ChartConfig from '../classes/ChartConfig';
import Sensor from '../classes/Sensor';
import ChartConfing from '../classes/ChartConfig';

@Component
export default class sensorView extends Vue {
    
    @Prop()
    private sensor: Sensor;
    private graphVisible: boolean;
    private chartConfig: any;
    private graph: Chart;


    public constructor() {
        super();
        this.graphVisible = false;
        this.chartConfig = new ChartConfing().chartConfing;
    }

    private get sensorName() {
        if(this.sensor == null) {
            return String.Empty;
        }
        return this.sensor.getName();
    }

    private onHeaderClick() : void {
        this.graphVisible = !this.graphVisible;
        
        if(this.graphVisible) {
            this.$emit('sensorActive', this.sensor);
            let canvas = this.$refs.canvas as HTMLCanvasElement;
            var ctx = canvas.getContext("2d");
            if(ctx != null) {
                this.graph = new Chart(ctx, this.chartConfig);
            }     
        } else {
            this.$emit('sensorInactive', this.sensor);
        }
    }
}
</script>

<style scoped>

#sensorViewContainer {
    float: left;
    width: calc(50% - 4px);
    margin: 2px;
    height: auto;
}

#sensorName {
    float: left;
}

#headerBar {
    position: relative;
    float: left;
    width: calc(100% - 10px);
    height: 20px;
    padding: 3px 5px 3px 5px;
    background-color: #EEE;
}

#headerBar:hover {
    background-color: #CCC;
}

.showIcon {
    height: 15px;
    width: 15px;
}

.showIconContainer {
    float: right;
    margin-top: 3px;    
}

#graphContainer {
    margin-top: 15px;
    position: relative;
    width: 100%;
    height: calc(100%/2);
    float: left;
}

</style>
