import Vue from 'vue';
import VueRouter from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';
import Store from './store';

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
    }
  ]
});

Router.beforeEach((to, from, next) => {
  Store.commit('setHeading', to.name);
  next();
})

export default Router;