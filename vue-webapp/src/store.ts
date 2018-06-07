import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import { String } from 'typescript-string-operations';
import Axios,{ AxiosPromise } from 'axios';
import Config from './appConfig.json';
import ResponseMessage from './classes/communication/ResponseMessage';

Vue.use(Vuex);

const Store = new Vuex.Store({
	
	state: {
		websocket: new WebSocket(),
		heading: String.Empty,
		loggedIn: false,
		token: localStorage.getItem("ACCESS_TOKEN")
	},

	getters: {
		getHeading: (state) : string => {
			return state.heading;
		},

		isLoggedIn: (state) : boolean => {
			return state.loggedIn;
		}
	},

	actions: {
		
 	},

	mutations: {
		setHeading(state, heading: string)  : void {
			state.heading = heading;
		},

		setLoggedIn(state, value: boolean) : void {
			state.loggedIn = value;
		}
	}
});

export default Store;