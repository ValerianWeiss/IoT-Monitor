<template>
	<div id="endpointOverview">
		<div id="overviewContainer">
			<h1>{{endpointName}}</h1>
			<div id="sensorViewContainer">
				<div v-for="sensor in sensors" :key="sensor.name">
					<sensorView v-bind:sensor="sensor"></sensorView>
				</div>
			</div>		
		</div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import Endpoint from '../classes/Endpoint';
import { String } from 'typescript-string-operations';
import SensorView from './sensorView.vue';
import Sensor from '../classes/Sensor';

@Component({
	components: {
		SensorView,
	}
})
export default class EndpointOverview extends Vue {

	@Prop()
	private endpoint: Endpoint;
	
	private get endpointName() : string {
		if(this.endpoint == null) {
			return String.Empty;
		}
		return this.endpoint.getName();
	}

	private get sensors() : Sensor[] {
		if(this.endpoint == null) {
			return [];
		}
		return this.endpoint.getSensors();
	}
}
</script>

<style scoped>

#overviewContainer {
	position: relative;
	margin: 0 20px 0 20px;
    float: left;
    width: calc(100% - 40px);
	height: calc(100vh - 192px);
	overflow-y: auto;
}

</style>
