import Vue from 'vue';
import VueRouter, { Route } from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import error from './components/error.vue';
import Store from './store';

Vue.use(VueRouter);

const Router = new VueRouter({
	mode: 'history',
	routes: [
		{   
			path: '/',
			name: 'login',
			component: login,			
			beforeEnter: (to: Route, from: Route, next: any) : void => {
				Store.getters.isTokenValid.then((result: boolean) => {
					if(result) {
						Router.push('/home');
					}
					next();
				}).catch((err: any) => {
					Router.push('/error');					
				});
			}
		},
		{
			path: '/home',
			name: 'home',
			component: home,
			beforeEnter: checkAuthentication,
		},
		{
			path: '/error',
			name: 'error',
			component: error,
		}
	]
});

Router.beforeEach((to: Route, from: Route, next: any) => {
	Store.commit('setHeading', to.name);
	next();
})


function checkAuthentication(to: Route, from: Route, next: any) {
	Store.getters.isTokenValid.then((result: boolean) => {
		if(!result) {
			Router.push('/');
		}
		next();
	}).catch((err: any) => {
		Router.push('/error');
		next();	
	});
}

export default Router;