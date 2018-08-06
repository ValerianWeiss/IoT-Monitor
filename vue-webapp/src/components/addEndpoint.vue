<template>
	<div id="addEndpoint">
		<div id="addEndpointContainer">
			<div id="form">
				<h1>Add new endpoint</h1>
				<input class="formInput" type="text" v-model="endpointName" placeholder="Endpoint name"/>
				<p class="hintText">{{endpointNameText}}</p>
				<textarea class="formInput" type="text" v-model="description" placeholder="Description of your endpoint..."/>
				<p class="hintText">{{descriptionText}}</p>

				<div v-for="index in sensorNames.length" :key="index">
					<input class="formInput" v-model="sensorNames[index-1]" placeholder="Sensor name"/>
					<p class="hintText">{{getSensorNameText(index-1)}}</p>
				</div>

				
				<div id="addSensor" @click="addSensor">
					<img id="addSensorImg" src="../recources/add.png" alt="Add sensor">
					<p id="addSensorText">Add Sensor</p>
				</div>
				<button class="btn" id="addBtn" type="button" @click="addEndpoint">
					<h3>Add Endpoint</h3>
				</button>
			</div>
			<div id="hintMessageBox" v-if="showHint">
				<p>A new Endpoint must have a name and at least one sensor.</p>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { String } from 'typescript-string-operations';
import Axios,{ AxiosResponse } from 'axios';
import Config from '../appConfig.json';
import ResponseMessage from '../classes/communication/ResponseMessage';
import { getAuthHeader } from '../store';

@Component
export default class AddEndpoint extends Vue {
	private endpointName: string;
	private description: string;
	private sensorNames: string[];
	private showHint: boolean;

	public constructor() {
		super();
		this.endpointName = String.Empty;
		this.description = String.Empty;
		this.sensorNames = [] as string[];
		this.sensorNames.push(String.Empty);
		this.showHint = false;
	}

	private get endpointNameText() : string {
		if(this.endpointName == String.Empty) {
			return String.Empty;
		}
		return 'Endpoint name';
	}

	private get descriptionText() : string {
		if(this.description == String.Empty) {
			return String.Empty;
		}
		return 'Description';
	}

	private getSensorNameText(index: number) : string {
		if(this.sensorNames[index] == String.Empty) {
			return String.Empty;
		}
		return 'Sensor name';
	}

	private sortSensorNames(list: string[]) : string[] {
		return this.sensorNames.sort((a: string, b: string) : number => {
			if(a < b) return -1;
    		if(a > b) return 1;
    		return 0;
		});
	}

	private removeDoubledItems(list: string[]) : string[] {
		for(let i = 0; i+1 < list.length; i++) {
			let index = i;
			let equalCounter = 0;

			while(list[index] == list[index+1]) {
				index += 1;
				if(index > list.length) {
					break;
				}
				equalCounter++;
			}
			list.splice(i+1, equalCounter);			
		}
		return list;
	}

	private removeEmptyItems(list: string[]) : string[] {
		let sensorNameExist = this.sensorNames.forEach((value: string, index: number, array: string[]) : void  => {
			if(value == String.Empty) {
				array.splice(index, 1);
			}
		});
		return list;
	}

	private addEndpoint() : void {
		
		let sortedSensorNames = this.sensorNames;
		sortedSensorNames = this.removeEmptyItems(sortedSensorNames)
		sortedSensorNames = this.sortSensorNames(sortedSensorNames);
		sortedSensorNames = this.removeDoubledItems(sortedSensorNames);

		if (this.endpointName != String.Empty && sortedSensorNames.length > 0 && sortedSensorNames != null) {
			
			Axios.post(Config.backendUrl + '/endpoint', {
				endpointName: this.endpointName,
				description: this.description,
				sensorNames: this.sensorNames,
			},
			{ 
				headers: getAuthHeader(),
			})
			.then((response: AxiosResponse<ResponseMessage>) : void =>{
				let data = response.data;
				if(data.success) {
					this.$emit('itemChanged', this.endpointName);
					this.$router.push('/home');
				}
			});
		}
	}

	private addSensor() : void {
		this.sensorNames.push(String.Empty);
	}
}
</script>

<style scoped>

#addEndpointContainer {
	overflow-y: auto;
	height: calc(100vh - 192px);
}

#form {
	position: relative;
	float: left;
	margin: 0 20px 0 20px;
	width: 300px;
	font-family: Arial, Helvetica, sans-serif;
}

.hintText {
	font-size: 10px;
	color: black;
	font-style: normal;
	font-weight: 600;
}

#hintMessageBox {
	position: relative;
	float: left;
	color: red;
	margin: 100px 20px 0 10px;
	width: 200px;
}

.formInput {
  	margin-top: 15px;
}

textarea {
	border: thin;
	border-style: solid;
	height: 50px;
}

#addBtn {
	border-radius: 8px;
	margin-top: 20px;
	height: 35px;
	width: 100%;
	background-color: #72d9b4;
}

#addBtn:hover {
  	background-color: #a8f5d9;
}

#addSensor {
	margin: 20px 0 0 0;
	height: 30px;
	width: 100%;
	background-color: #EEE;	
	border-radius: 8px;
}

#addSensorImg {
	position: relative;
	float: left;
	margin: 7px 5px 7px 10px;
	height: 15px;
	width: 15px;
}

#addSensor:hover {
  	background-color: #CCC;
}

#addSensorText {
	float: left;
	margin: 8px 0 0 5px;
	font-size: 12px;
	font-weight: 700;
	text-transform: uppercase;
}
</style>
