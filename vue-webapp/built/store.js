import Vue from 'vue';
import Vuex from 'vuex';
import WebSocket from './classes/WebSocket';
import User from './classes/User';
Vue.use(Vuex);
var Store = new Vuex.Store({
    state: {
        websocket: new WebSocket(),
        user: new User()
    },
    getters: {
        myOtherGetter: function (state) {
            return state.user;
        }
    },
    actions: {
        setUser: function (_a, payload) {
            var commit = _a.commit, getters = _a.getters;
            commit('setUser');
        }
    },
    mutations: {
        setUser: function (state, user) {
            state.user = user;
        }
    }
});
export default Store;
//# sourceMappingURL=store.js.map