import Vue from 'vue';
import VueRouter from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import error from './components/error.vue';
import { onHttpConnectionError } from './classes/communication/Error';
import Store from './store';
import ResponseMessage from './classes/communication/ResponseMessage';
import { AxiosResponse } from 'axios';

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
	if(Store.getters.isLoggedIn && to.name != 'login') {
		console.log("push to " + to.path + " heading : " + to.name);
		Store.commit('setHeading', to.name);
	}
	next();
});

export default Router;