import VueRouter from 'vue-router';
import login from '../components/login.vue';
import home from '../components/home.vue';
var router = new VueRouter({
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
export default router;
//# sourceMappingURL=router.js.map