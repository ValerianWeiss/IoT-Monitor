import VueRouter from 'vue-router';
import App from '../App.vue';
import navigationbar from '../components/navigationbar.vue';
import sideheading from '../components/sideheading.vue';
import login from '../components/login.vue';
import home from '../components/home.vue'

const router = new VueRouter({
  mode: 'history',
  routes: [
    {   
        path: '/',
        component: login,
    },
    {
        path: '/home',
        component: home,
    }
  ]
});
export default router