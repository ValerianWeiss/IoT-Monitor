<template>
  <div id="home">
       <navigationbar></navigationbar>
       <div id="main">
           <h1 id="mainHeading">Content</h1>
           <div id="lineSeperator"/>
           <button @click="createGraphView">test</button>
           <div id="graphContainer" ref="graphContainerRef">
                <GraphView v-show="true" v-for="count in graphCount" :key="count" v-on:graphViewMounted="initGraphView"/>
           </div>
       </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Navigationbar from './navigationbar.vue';
import GraphView from './graphView.vue';

@Component({
    components: {
        Navigationbar,
        GraphView,
    }
})
export default class Home extends Vue {
    
    private graphCounter: number;

    public get graphCount() : number{
        return this.graphCounter;
    }

    public constructor() {
        super();
        this.graphCounter = 0;
    }

    private createGraphView() {
        this.graphCounter++;
    }

    private initGraphView(graph: GraphView) : void {
        graph.setText("Hello Wrold " + this.graphCounter);
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
    margin-bottom: 30px;
    font-weight: 600;
}

#lineSeperator {
    margin: 0;
    width: 100%;
    background-color: black;
    height: 2px;
}

</style>

