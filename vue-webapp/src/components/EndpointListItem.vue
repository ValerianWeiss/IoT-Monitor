<template>
    <div id="endpointListItem">
        <img src="../recources/device.png" id="deviceImg">
        <p id="itemName">{{endpointName}}</p>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import Endpoint from '../classes/Endpoint';

@Component
export default class EndpointListItem extends Vue {
 
    public constructor() {
        super();
    }
    
    @Prop()
    endpoint: Endpoint;

    private get endpointName() : string {
        console.log(this.endpoint);
        return this.endpoint.getName();
    }
}
</script>

<style scoped>

#endpointListItem {
    position: relative;
    height: 25px;
    margin-bottom: 8px;
    border: none;
    width: 100%;
}

#endpointListItem:hover {
    background-color: #DDD;
}

#itemName {
    position: relative;
    float: left;
    margin: 3px 10px 0 1px;
    font-weight: 100;
    font-size: 15px;
}

#deviceImg {
    margin-left: 5px;
    float: left;
    height: 23px;
    width: 23px;
}
</style>
