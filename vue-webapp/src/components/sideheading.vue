<template>
	<div id="root">
		<div id="sideHadingContainer">
			<h1 id="sideHeading">{{heading}}</h1>        
		</div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import { Prop } from 'vue-property-decorator'

@Component
export default class Sideheading extends Vue {
	@Prop() heading: string;

	constructor(){
		super();
	}

}
</script>

<style scoped>

#root{
	position: relative;
    float: left;
}

#sideHadingContainer{
	height: auto;
    width: 100px;
    margin: 0;
    padding: 300px 60px 20px 20px;
}

#sideHeading{
	font-family: 'Archivo Black', sans-serif;
	font-size: 600%;
	text-align: right;
	transform: rotate(90deg);
	text-transform: uppercase;
}
</style>

