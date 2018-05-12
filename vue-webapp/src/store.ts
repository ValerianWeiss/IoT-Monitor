import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import User from './classes/User';
import { String } from 'typescript-string-operations';
import Axios,{ AxiosPromise } from 'axios';
import Config from './appConfig.json';
import ResponseMessage from './classes/communication/ResponseMessage';

Vue.use(Vuex);

const Store = new Vuex.Store({
	
	state: {
		websocket: new WebSocket(),
		user: new User(),
		heading: String.Empty,
	},

	getters: {
		getUser: (state) : User  => {
			return state.user;
		},

		getHeading: (state) : string => {
			return state.heading;
		},

		isLoggedIn (state) : AxiosPromise<ResponseMessage> {
			return Axios.get(Config.backendUrl + '/user/token', {
				params: {
				    username: state.user.getUsername(),
				    sessionToken : state.user.getSessionToken(),
				}
			});
		}
	},

	actions: {
		
 	},

	mutations: {
		setUser(state, user: User) : void {
			state.user = user;
		},

		setHeading(state, heading: string)  : void {
			state.heading = heading;
		}
	}
});

export default Store;