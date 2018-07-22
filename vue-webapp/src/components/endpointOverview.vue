<template>
	<div id="endpointOverview">
		<h1>{{endpointName}}</h1>		
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import Endpoint from '../classes/Endpoint';
import { String } from 'typescript-string-operations';

@Component
export default class EndpointOverView extends Vue {

	@Prop()
	private endpoint: Endpoint;
	
	private get endpointName() : string {
		if(this.endpoint == null) {
			return String.Empty;
		}
		return this.endpoint.getName();
	}

	mounted () {
		if(this.endpoint != null)
			console.log("heading should be "  + this.endpoint.getName());
		
	}
}
</script>

<style scoped>

#endpointOverview {
    float: left;
    width: calc(100% - 250px);
	height: auto;
	background-color: blueviolet;
	height: calc(100vh - 155px);
	overflow: auto;
}

</style>
