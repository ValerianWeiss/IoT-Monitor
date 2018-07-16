import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import { String } from 'typescript-string-operations';
import Axios from 'axios';
import Config from './appConfig.json';
import * as JWT from 'jwt-decode';

Vue.use(Vuex);

const Store = new Vuex.Store({
	
	state: {
		websocket: new WebSocket(),
		heading: String.Empty,
	},

	getters: {
		getHeading: (state) : string => {
			return state.heading;
		},

		getAuthHeader: (state) : any => {
			return { Authorization : "Bearer " + localStorage.getItem(Config.tokenEntity), "Access-Control-Allow-Origin" : "*" };
		},

		getUsername: (state) : String | null => {	
			let token = localStorage.getItem(Config.tokenEntity);
			if(token != null) {
				let data: any = JWT(token);
				return data["username"] as string;
			}
			return null;			
		},

		isTokenValid: async (state: any) : Promise<boolean> => {
			let token = localStorage.getItem(Config.tokenEntity);
			if (token != null) {
				let response = await Axios.post(Config.backendAuthUrl + '/user/isTokenValid/', {token});
				return response.data;
			}
			return false;
		}
	},

	actions: {
		
	},

	mutations: {
		setHeading(state, heading: string) : void {
			state.heading = heading;
		},

		deleteToken(state) : void {
			localStorage.removeItem(Config.tokenEntity);
		},

		subscribe(state, subInfo: any) : void {
			state.websocket.subscribe(subInfo.topic, subInfo.callback);
		},

		unsubscribe(state) : void {
			state.websocket.unsubscribe();
		},
	}
});

export default Store;