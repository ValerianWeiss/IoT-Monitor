<template>
	<div id="endpointOverview">
		<div id="overviewContainer">
			<h1>{{endpointName}}</h1>		
		</div>
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
}
</script>

<style scoped>

#overviewContainer {
	position: relative;
	margin: 0 10px 0 10px;
    float: left;
    width: calc(100% - 20px);
	background-color: blueviolet;
	height: calc(100vh - 192px);
	overflow-y: auto;
}

</style>
