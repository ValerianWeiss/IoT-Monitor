import Vue from 'vue';
import app from './app.vue';
import router from './router';
import store from './store';
new Vue({
    el: '#app',
    router: router,
    store: store,
    render: function (h) { return h(app); }
});
//# sourceMappingURL=main.js.map