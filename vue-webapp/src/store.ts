import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import { String } from 'typescript-string-operations';
import Axios,{ AxiosPromise, AxiosResponse } from 'axios';
import Config from './appConfig.json';
import ResponseMessage from './classes/communication/ResponseMessage';
import * as JWT from 'jwt-decode';

Vue.use(Vuex);

const Store = new Vuex.Store({
	
	state: {
		websocket: new WebSocket(),
		heading: String.Empty,
		tokenData: {},
		token: localStorage.getItem(Config.tokenEntity)
	},

	getters: {
		getHeading: (state) : string => {
			return state.heading;
		},

		isTokenValid: async (state : any) : Promise<boolean> => {
			if (state.token != null) {
				let data :any = JWT(state.token);
				state.tokenData = data;
				
				if(new Date().getMilliseconds() < data["exp"]) {
					let response = await Axios.put(Config.backendAuthUrl + 'user/refreshToken/',
						{
							"token" : state.token
						})
					
					if(response.data.success) {
						localStorage.setItem(Config.tokenEntity, response.data.payload);
						return true;
					}
					localStorage.removeItem(Config.tokenEntity);
				}
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
	}
});


export default Store;