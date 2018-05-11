import Vue from 'vue';
import VueRouter from 'vue-router';
import login from './components/login.vue';
import home from './components/home.vue';

Vue.use(VueRouter);

const Router = new VueRouter({
  mode: 'history',
  routes: [
    {   
        path: '/',
        name: 'root',
        component: login,
    },
    {
        path: '/home',
        name: 'home',
        component: home,
    }
  ]
});

export default Router;