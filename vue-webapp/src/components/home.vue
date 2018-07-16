<template>
  <div id="home">
        <navigationbar></navigationbar>
        <div id="main">
            <h1 id="mainHeading">Content</h1>
            <div id="lineSeperator"/>
            <button class="graphButton" v-for="topic in graphTopics" :key=topic
                    @click="createGraphView(topic)">Show Graph {{topic}}
                </button>
            <div class="graphContainer">
                    <GraphView v-show="true"
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
import GraphView from './graphView.vue';
import Axios from 'axios';

@Component({
    components: {
        Navigationbar,
        GraphView,
    }
})
export default class Home extends Vue {
    
    private graphMapper: Map<number, string>;
    private graphViews: GraphView[];
    private graphTopics: string[];
    private graphCounter: number;

    public get graphCount() : number {
        return this.graphCounter;
    }

    public constructor() {
        super();
        this.graphViews = [];
        this.graphMapper = new Map();
        this.graphCounter = 0;
        this.graphTopics = ['graph/rand', 'graph/test'];
    }

    private createGraphView(topic: string) : void {
        this.graphMapper.set( this.graphMapper.size, topic);
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
}
</script>

<style scoped>

#main {
    width: 80vw;
    padding: 30px;
    margin: auto;
}

#mainHeading {
    margin-bottom: 10px;
    font-weight: 600;
}

#lineSeperator {
    position: relative;
    margin: 0;
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

