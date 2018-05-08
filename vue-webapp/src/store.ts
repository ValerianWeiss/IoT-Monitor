import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import User from './classes/User';

Vue.use(Vuex);

const Store = new Vuex.Store({
	state: {
		websocket: new WebSocket(),
		user: new User()
	},

	getters: {
		myOtherGetter(state) : User {
			return state.user;
		}
	},

	actions: {
		setUser({commit, getters}, payload) {
			commit('setUser');
		}
	},

	mutations: {
		setUser(state, user: User) : void {
			state.user = user;
		}
	}
});

export default Store;