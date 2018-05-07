import Vue from 'vue';
import App from './app.vue';
import router from './router/router';
import VueRouter from 'vue-router';
Vue.use(VueRouter);
new Vue({
    el: '#app',
    router: router,
    render: function (h) { return h(App); }
});
//# sourceMappingURL=main.js.map