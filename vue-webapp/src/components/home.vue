<template>
    <div id="home">
        <navigationbar></navigationbar>
        <div id="main">
            <h1 id="mainHeading">Overview</h1>
            <div id="lineSeperator"></div>
                <div id="endpointList">
                    <h3 id="endpointListHeading">Endpoints</h3>
                    <input id="endpointsearch" type="text" placeholder="Search..."/>
                    <div id="listItemContainer">
                        <endpointListItem 
                            v-for="endpoint in endpoints" :key=endpoint.name
                            v-bind:endpoint="endpoint"
                            v-on:activeEndpointChanged="onActiveEndpointChanged"/>
                    </div>
                </div>

                <div id="actionBar">
                    <div id="monitorIconContainer" class="barIconContainer" @click="onMonitorclick">
                        <img class="barIcon" id="monitorIcon" src="../recources/monitor.png" alt="Show overview">
                    </div>
                    <div class="barIconContainer" @click="onAddnewEndpoint">
                        <img class="barIcon" src="../recources/add.png" alt="Add new endpoint">
                    </div>
                    <div class="barIconContainer">
                        <img class="barIcon" src="../recources/edit.png" alt="Edit endpoint">
                    </div>
                    <div class="barIconContainer">
                        <img class="barIcon" src="../recources/info.png" alt="About endpoint">
                    </div>
                </div>
                
                <router-view id="contentView"
                             v-bind:endpoint="activeEndpoint"
                             v-on:itemChanged="onItemChange">
                </router-view>
        </div>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Navigationbar from './navigationbar.vue';
import EndpointListItem from './endpointListItem.vue';
import Config from '../appConfig.json';
import Axios, { AxiosResponse } from 'axios';
import Endpoint from '../classes/Endpoint';
import { Route } from 'vue-router';
import EndointOverview from './endpointOverview.vue';
import Sensor from '../classes/Sensor';

@Component({
    components: {
        Navigationbar,
        EndpointListItem,
        EndointOverview,
    }
})
export default class Home extends Vue {
    
    private graphMapper: Map<number, string>;
    private endpoints: Endpoint[];
    private activeEndpoint: Endpoint | null;


    public constructor() {
        super();
        this.graphMapper = new Map();
        this.endpoints = [] as Endpoint[];      
        this.activeEndpoint = null;
    }

    private onAddnewEndpoint() {
        this.$router.push('home/addEndpoint');
    }

    private onMonitorclick() : void {
        this.$router.push('/home');
    }

    private getEndpoints() : Promise<void> {

        this.endpoints = [] as Endpoint[];
        
        return Axios.get(Config.backendRecourceUrl + '/user/' + this.$store.getters.username + '/endpoint/all',
            { 
                headers : this.$store.getters.authHeader
            })
            .then((response: AxiosResponse) => {
                let data = response.data;
                if(data.success) {
                    
                    let resendpoints: any[] = data.payload.endpoints;
                    
                    for(let i = 0; i < resendpoints.length; i++) {
                        let endpoint = new Endpoint(resendpoints[i].name,
                                                         resendpoints[i].description,
                                                         resendpoints[i].token);

                        for(let s = 0; s < resendpoints[i].sensors.length; s++) {
                            let respSensor = resendpoints[i].sensors[s];
                            let sensor = new Sensor(respSensor.name, respSensor.topic);
                            endpoint.addSensor(sensor);
                        }
                        this.endpoints.push(endpoint);
                    }
                    this.sortEndpointList();                    
                }
            });
    }

    private sortEndpointList() {
        this.endpoints.sort((a: Endpoint, b: Endpoint) : number => {
            let aName = a.getName().toLowerCase();
            let bName = b.getName().toLowerCase();
			if(aName < bName) return -1;
    		if(aName > bName) return 1;
    		return 0;
		});
    }

    private onItemChange(endpointName: string) {

        this.getEndpoints().then(() => {
            this.endpoints.forEach((endpoint: Endpoint, index: number,array: Endpoint[]) => {
                if(endpoint.getName() == endpointName) {
                    this.activeEndpoint = endpoint;
                    return;
                }
            });            
        });
    }

    private onActiveEndpointChanged(endpoint: Endpoint) : void {
        this.activeEndpoint = endpoint;
    }

    private beforeMount () : void {
        this.getEndpoints().then(() => {
            if(this.endpoints.length > 0) {
                this.activeEndpoint = this.endpoints[0];
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

#endpointsearch {
    margin-left: 10px;
    width: 220px;
    margin-bottom: 20px;
    border-radius: 10px;
    height: 20px;
    border: thin;
    padding-left: 5px;
}

#endpointsearch::placeholder {
    color: dimgray;
    font-style: italic;
}

#endpointListHeading {
    margin: 5px 0 10px 10px;
}

#endpointList {
    position: fixed;
    height: calc(100vh - 152px);
    width: 250px;
    float: left;
    background-color: #EEE;
}

#listItemContainer {
    overflow-y: auto;
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

#actionBar {
    margin-left: 250px;
	width: calc(100% - 250px);
	height: 40px;
	background-color: #EEE;
}

.barIcon {
	height: 20px;
	width: 20px;
}

.barIconContainer {
	float: left;
	height: 20px;
	width: 20px;
	padding: 10px;
}

.barIconContainer:hover {
	background-color: #CCC;
}

#contentView {
    margin-left: 250px;
}

#monitorIconContainer {
    width: 30px;
}

#monitorIcon {
    width: 30px;
}
</style>

