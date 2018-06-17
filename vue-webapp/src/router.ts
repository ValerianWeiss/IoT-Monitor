import Vue from 'vue';
import VueRouter, { Route } from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import error from './components/error.vue';
import { onHttpConnectionError } from './classes/communication/Error';
import Store from './store';
import ResponseMessage from './classes/communication/ResponseMessage';
import Axios, { AxiosResponse } from 'axios';
import { log } from 'util';

Vue.use(VueRouter);

const Router = new VueRouter({
	mode: 'history',
	routes: [
		{   
			path: '/',
			name: 'login',
			component: login
		},
		{
			path: '/home',
			name: 'home',
			component: home,
			beforeEnter: checkAuthentication
		},
		{
			path: '/error',
			name: 'error',
			component: error,
		}
	]
});


function checkAuthentication (to: Route, from: Route, next: any) {
	//Store.getters.isTokenValid.then()
}


export default Router;