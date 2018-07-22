<template>
	<div class="sensorView">
        <div id="sensorViewContainer">
            <div id="headerBar">
                <h3 id="sensorName">{{sensor.getName()}}</h3>
            </div>
        </div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { String } from 'typescript-string-operations';
import Sensor from '../classes/Sensor';

@Component
export default class sensorView extends Vue {
    
    @Prop()
    private sensor: Sensor;


    public constructor() {
        super();
    }

    private get sensorName() {
        if(this.sensor == null) {
            return String.Empty;
        }
        return this.sensor.getName();
    }
}
</script>

<style scoped>

#headerBar {
    float: left;
    width: calc(50% - 10px);
    height: 20px;
    padding: 3px 5px 3px 5px;
    background-color: #EEE;
}

#headerBar:hover {
    background-color: #CCC;
}

</style>
