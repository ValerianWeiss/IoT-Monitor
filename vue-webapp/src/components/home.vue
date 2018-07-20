<template>
  <div id="home">
        <navigationbar></navigationbar>
        <div id="main">
            <h1 id="mainHeading">Overview</h1>
            <div id="lineSeperator"></div>
            <div id="deviceList">
                <h3 id="deviceListHeading">Endpoints</h3>
                <input id="deviceSearch" type="text" placeholder="Search..."/>
                <div id="listItemContainer">
                    <endpointListItem 
                        v-for="device in devices" :key=device.name
                        v-bind:endpoint="device"/>
                </div>
            </div>
            <button class="graphButton" v-for="topic in graphTopics" :key=topic
                    @click="createGraphView(topic)">Show Graph {{topic}}
            </button>
            <div class="graphContainer">
                    <graphView
                        v-for="count in graphCount" :key=count
                        v-on:graphViewMounted="graphViewMounted"/>
            </div>
        </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Navigationbar from './navigationbar.vue';
import EndpointListItem from './endpointListItem.vue';
import GraphView from './graphView.vue';
import Device from '../classes/Endpoint';
import Config from '../appConfig.json';
import Axios, { AxiosResponse } from 'axios';
import Endpoint from '../classes/Endpoint';

@Component({
    components: {
        Navigationbar,
        GraphView,
        EndpointListItem,
    }
})
export default class Home extends Vue {
    
    private graphMapper: Map<number, string>;
    private graphViews: GraphView[];
    private graphTopics: string[];
    private graphCounter: number;
    private devices: Device[];
   

    public constructor() {
        super();
        this.graphViews = [];
        this.graphMapper = new Map();
        this.graphCounter = 0;
        this.graphTopics = ['graph/rand', 'graph/test'];
        this.devices = [];
        this.getDevices();
    }

     private get graphCount() : number {
        return this.graphCounter;
    }

    private createGraphView(topic: string) : void {
        this.graphMapper.set(this.graphMapper.size, topic);
        this.graphCounter = this.graphMapper.size;
    }

    private graphViewMounted(graphView: GraphView) : void {
        let topic = this.graphMapper.get(this.graphViews.length);
        this.graphViews.push(graphView);
        graphView.setHeading('Hello World ' + this.graphViews.length);
        if(topic != undefined) {
            graphView.addDataEndpoint(topic);
            graphView.setName(topic);
        } else {
            this.$router.push('/error');
        }
    }

    private getDevices() : void {
        Axios.get(Config.backendRecourceUrl + '/user/' + this.$store.getters.username + '/device/all',
            { 
                headers : this.$store.getters.authHeader
            }).then((response: AxiosResponse) => {
                let data = response.data;
                if(data.success) {
                    let resDevices: any[] = data.payload;
                    resDevices.forEach((device: any) => {
                        this.devices.push(new Endpoint(device.name, device.description, device.token));
                    })
                }
            });
    }
}
</script>

<style scoped>

#main {
    position: relative;
    margin-left: 180px;
    width: calc(100% - 180px);
    height: calc(100% - 60px);
    padding: 0;
}

#mainHeading {
    margin-bottom: 10px;
    font-weight: 600;
}

#deviceSearch {
    margin-left: 10px;
    width: 220px;
    margin-bottom: 20px;
    border-radius: 10px;
    height: 20px;
    border: thin;
    padding-left: 5px;
}

#deviceSearch::placeholder {
    color: dimgray;
    font-style: italic;
}

#deviceListHeading {
    margin: 5px 0 10px 10px;
}

#deviceList {
    position: relative;
    height: calc(100vh - 155px);
    width: 250px;
    margin-right: 10px;
    float: left;
    background-color: #EEE;
}

#listItemContainer {
    overflow-x: auto;
    width: 100%;
    height: calc(100% - 71px);
}

#lineSeperator {
    position: relative;
    margin-left: 0px;
    width: 100%;
    background-color: black;
    height: 2px;
    margin-bottom: 20px;
}

.graphButton {
    position: relative;
    width: 50%;
    height: 30px;
    background-color: white;
}

</style>

