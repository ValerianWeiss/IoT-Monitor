<template>
	<div id="endpointInfo">
        <div id="infoContainer">
            <h1>{{endpointName}}</h1>
            <div id="tokenContainer">
                <h3 id="tokenHeading">Token</h3>
                <p id="token">{{token}}</p>
            </div>
            <h3 id="sensorsHeading">Sensors</h3>
            <div v-for="sensor in sensors" :key="sensor.topic">
                <div class="sensor">
                    <div class="attrKeyVal">
                        <h4 class="attrHeading">Name:</h4>
                        <p class="attr">{{sensor.name}}</p>
                    </div>
                    <div class="attrKeyVal">
                        <h4 class="attrHeading">Topic:</h4>
                        <p class="attr">{{sensor.topic}}</p>
                    </div>
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
import Sensor from '../classes/Sensor';

@Component({
	components: {
	}
})
export default class EndpointInfo extends Vue {
    
    @Prop()
    private endpoint: Endpoint;
    
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
    
    private get token() : String {
        if(this.endpoint != null) {
            let token = this.endpoint.getToken();
        
            if(token != null) {
                return 'Baerer ' + token;
            }
        }
        return String.Empty;
    }
}
</script>

<style scoped>
 
#infoContainer {
    position: relative;
	padding: 0 20px 0 20px;
    float: left;
    width: calc(100% - 40px);
	height: calc(100vh - 192px);
	overflow-y: auto;
}

#tokenContainer {
    position: relative;
    width: 100%;
}

#token {
    margin-top: 3px;
    word-break: break-all;
}

#sensorsHeading {
    margin: 15px 0 10px 0;
}

.attrKeyVal {
    margin-bottom: 3px;
}

.attrHeading {
    margin-right: 3px;
    position: relative;
    float: left;
}

.sensor {
    margin-bottom: 10px;
    position: relative;
}

</style>
