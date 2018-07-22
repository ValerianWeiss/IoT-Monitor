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
                            v-bind:endpoint="endpoint"/>
                    </div>
                </div>

                <div id="actionBar">
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
import GraphView from './graphView.vue';
import Config from '../appConfig.json';
import Axios, { AxiosResponse } from 'axios';
import Endpoint from '../classes/Endpoint';
import { Route } from 'vue-router';
import EndointOverview from './endpointOverview.vue';
import value from '../appConfig.json';

@Component({
    components: {
        Navigationbar,
        GraphView,
        EndpointListItem,
        EndointOverview,
    }
})
export default class Home extends Vue {
    
    private graphMapper: Map<number, string>;
    private graphViews: GraphView[];
    private graphTopics: string[];
    private graphCounter: number;
    private endpoints: Endpoint[];
    private activeEndpoint: Endpoint | null;


    public constructor() {
        super();
        this.graphViews = [];
        this.graphMapper = new Map();
        this.graphCounter = 0;
        this.graphTopics = ['graph/rand', 'graph/test'];
        this.endpoints = [] as Endpoint[];      
        this.activeEndpoint = null;
          
    }

    private get graphCount() : number {
        return this.graphCounter;
    }

    private onAddnewEndpoint() {
        this.$router.push('home/addEndpoint');
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
            graphView.addDatapoint(topic);
            graphView.setName(topic);
        } else {
            this.$router.push('/error');
        }
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
                        this.endpoints.push(new Endpoint(resendpoints[i].name,
                                                         resendpoints[i].description,
                                                         resendpoints[i].token,
                                                         resendpoints[i].sensors));
                    }
                    this.sortEndpointList();                    
                }
            });
    }

    private sortEndpointList() {
        this.endpoints.sort((a: Endpoint, b: Endpoint) : number => {
			if(a.getName() < b.getName()) return -1;
    		if(a.getName() > b.getName()) return 1;
    		return 0;
		});
    }

    private onItemChange(endpointName: string) {

        let newActiveEndpoint: Endpoint;

        this.getEndpoints().then(() => {
            this.endpoints.forEach((endpoint: Endpoint, index: number,array: Endpoint[]) => {
                if(endpoint.getName() == endpointName) {
                    newActiveEndpoint = endpoint;
                    return;
                }
            });            
        });
    }

    private beforeMount () : void {
        this.getEndpoints();
        if(this.endpoints.length > 0) {
            this.activeEndpoint = this.endpoints[0];
        }
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
    height: calc(100vh - 155px);
    width: 250px;
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
</style>

