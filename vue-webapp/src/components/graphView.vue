<template>
	<div class="graphView">
        <div class="graphWrapper">
            <h2>{{text}}</h2>
        </div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';

@Component
export default class GraphView extends Vue {
    
    private text:string;
    
    public constructor() {
        super();
        this.text = "";
    }

    public setText(text: string) : void {
        this.text = text;
    }
            
    private mounted () {
        this.$store.commit('subscribe', {topic:'graph', callback: () => {
            console.log("got an socket message on topic graph");
        }});
        this.$emit('graphViewMounted', this);
    }
}
</script>

<style scoped>
.graphWrapper {
    float: left;
    border: 1px;
    border-style: solid;
    border-color: black;
    height: 40vh;
    width: calc(50% - 2px);
    background-color: yellow;
}
</style>
