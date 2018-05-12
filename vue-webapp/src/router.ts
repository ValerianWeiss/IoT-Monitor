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
	if(to.name != 'login') {
		Store.getters.isLoggedIn.
			then((response : AxiosResponse<ResponseMessage>) : void => {
				if(response.data.success) {
					Router.push(to);
					Store.commit('setHeading', to.name);
				} else {
					Router.push('/');
					Store.commit('setHeading', 'login');
				}
			}).catch(onHttpConnectionError);
	} else {
		Store.commit('setHeading', "login");
	}
	next();
});

export default Router;