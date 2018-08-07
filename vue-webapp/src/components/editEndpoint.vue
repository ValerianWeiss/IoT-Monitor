<template>
	<div id="editEndpoint">
        <div id="editEndpointContainer" v-if="endpoint!=null">
            <div id="deleteContainer">
                <h1 id="endpointName">{{endpointName}}</h1>
                <button class="btn deleteBtn" @click="deleteEndpoint"><h3>Delete</h3></button>
                <h3 id="sensorsHeading">Sensors</h3>
                <div v-for="sensor in sensors" :key="sensor.topic">
                    <div class="sensor">
                        <div id="sensorContainer">
                            <div class="attrKeyVal">
                                <h4 class="attrHeading">Name:</h4>
                                <p class="attr">{{sensor.name}}</p>
                            </div>
                            <div class="attrKeyVal">
                                <h4 class="attrHeading">Topic:</h4>
                                <p class="attr">{{sensor.topic}}</p>
                            </div>
                        </div>
                        <button class="btn deleteBtn sensorDeleteBtn" @click="deleteSensor(sensor)">
                            Delete
                        </button>
                    </div>
                </div>
            </div>
            <div id="addContainer">
                <div v-for="index in sensorNames.length" :key="index">
					<input class="formInput" v-model="sensorNames[index-1]" placeholder="Sensor name"/>
					<p class="hintText">{{getSensorNameText(index-1)}}</p>
				</div>
				<div id="addSensor" @click="addSensor">
					<img id="addSensorImg" src="../recources/add.png" alt="Add sensor">
					<p id="addSensorText">Add Sensor</p>
				</div>
                <button class="btn" id="addBtn" type="button" @click="addAllSensors">
					<h3>Add all</h3>
				</button>
            </div>
        </div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import Endpoint from '../classes/Endpoint';
import { String } from 'typescript-string-operations';
import Sensor from '../classes/Sensor';
import Axios,{ AxiosPromise } from 'axios';
import Config from '../appConfig.json';
import { getAuthHeader } from '../store';

@Component({
	components: {
	}
})
export default class EditEndpoint extends Vue {

    @Prop()
    private endpoint: Endpoint;
    private sensorNames: string[];

    public constructor() {
        super();
        this.sensorNames = [];
        this.sensorNames.push(String.Empty);
    }

    private get endpointName() : string {
		if(this.endpoint == null) {
			return String.Empty;
		}
		return this.endpoint.getName();
    }

    private get sensors() : Sensor[] {
        if(this.endpoint != null) {
            return this.endpoint.getSensors();
        }
        return [];
    }

    private getSensorNameText(index: number) : string {
		if(this.sensorNames[index] == String.Empty) {
			return String.Empty;
		}
		return 'Sensor name';
    }
    
    private addSensor() : void {
		this.sensorNames.push(String.Empty);
    }
    
    private addAllSensors() : void {
        
        let toadd: string[] = [];

        this.sensorNames.forEach((sensorName: string) => {
            if(sensorName != String.Empty) {
                toadd.push(sensorName);
            }
        });

        Axios.post(Config.backendUrl + '/sensor', {
            endpointName: this.endpoint.getName(),
            description: null,
            sensorNames: toadd,

        }, {
            headers : getAuthHeader(),
        }).then(() => {
            this.$emit('itemChanged', this.endpoint.getName());
        });
    }

    private deleteEndpoint() {
        Axios.delete(Config.backendUrl + '/endpoint/' + this.endpoint.getName(), {
            headers : getAuthHeader(),
        }).then(() => {
            this.$emit('itemChanged', this.endpoint.getName());
            this.$router.push('/home');
        });
    }

    private deleteSensor(sensor: Sensor) {
        Axios.delete(Config.backendUrl + '/sensor/' 
            + this.endpoint.getName()
            + '/' + sensor.getName(),
            {
                headers : getAuthHeader(),
            }
        ).then(() => {
            this.$emit('itemChanged', this.endpoint.getName());
        });
    }
}
</script>

<style scoped>

#editEndpointContainer {
    position: relative;
    padding: 0 20px 0 20px;
}

#endpointName {
    float: left;
    margin-right: 50px;
}

#deleteContainer {
    width: calc(33% - 20px);
    float: left;
}

#addContainer {
    width: calc(33% - 20px);
    float: left;
    margin-top: 80px;
}

#sensorContainer {
    position: relative;
    float: left;
}

#sensorsHeading {
    margin: 15px 0 10px 0;
}

.deleteBtn {
    position: relative;
    margin: 22px 20px 29px 0px;
    height: 30px;
    width: 100px;
    background-color: #FF574E;
}

.deleteBtn:hover {
    background-color: rgb(245, 134, 129);
}

.sensorDeleteBtn{
    position: absolute;
    margin: 0;
    left: 280px;
    height: 30px;
    width: 70px;
    font-family: 'Archivo', sans-serif;
    font-weight: 800;
    font-size: 12px;
}

.attr {
    margin-left: 40px;
}

.attrKeyVal {
    margin-bottom: 3px;
    white-space: nowrap;
}

.attrHeading {
    margin-right: 3px;
    position: relative;
    float: left;
}

.sensor {
    margin-bottom: 10px;
    position: relative;
    height: 40px;
}

.hintText {
	font-size: 10px;
	color: black;
	font-style: normal;
	font-weight: 600;
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

.formInput {
  	margin-top: 15px;
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
</style>
