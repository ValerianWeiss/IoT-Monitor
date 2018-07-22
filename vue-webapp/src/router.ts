import Vue from 'vue';
import VueRouter, { Route } from 'vue-router';
import Login from './components/login.vue';
import Home from './components/home.vue';
import Error from './components/error.vue';
import EndpointOverview from './components/endpointOverview.vue';
import AddEndpoint from './components/addEndpoint.vue'
import Store from './store';

Vue.use(VueRouter);

const Router = new VueRouter({
	mode: 'history',
	routes: [
		{   
			path: '/',
			name: 'login',
			component: Login,			
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
			component: Home,
			beforeEnter: checkAuthentication,			
			children: [
				{
					path: '',
					component: EndpointOverview,
					name: 'home', 
				},

				{
					path: 'addEndpoint',
					component: AddEndpoint,
					name: 'add',
				},

				{
					path: 'editEndpoint',
					name: 'edit',
					//component:
				},

				{
					path: 'endpointInfo',
					name: 'info'
					//component:
				}
			]
		},
		{
			path: '/error',
			name: 'error',
			component: Error,
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