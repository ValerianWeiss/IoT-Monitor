import Vue from 'vue';
import VueRouter from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import error from './components/error.vue';
import { onHttpConnectionError } from './classes/communication/Error';
import Store from './store';
import ResponseMessage from './classes/communication/ResponseMessage';
import { AxiosResponse } from 'axios';
import { log } from 'util';

Vue.use(VueRouter);

const Router = new VueRouter({
	mode: 'history',
	routes: [
		{   
			path: '/',
			name: 'login',
			component: login,
		},
		{
			path: '/home',
			name: 'home',
			component: home,
		},
		{
			path: '/error',
			name: 'error',
			component: error,
		}
	]
});

Router.beforeEach((to, from, next) => {
	let heading: string | undefined;

	if(Store.getters.isLoggedIn) {
		heading = to.name;
	} else if(to.name == 'login') {
		heading = to.name;
	} else if(Store.getters.isLoggedIn && to.name == 'login') {
		Router.push('/home')
		heading = 'home';
	} else {
		Router.push('/');
		heading = 'login';
	}

	Store.commit('setHeading', heading);
	next();
});

export default Router;