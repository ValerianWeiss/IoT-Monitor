<template>
    <div id="navigationbar">
        <nav id="wrapper">
            <button class="btn" id="logoutBtn" @click="logout">
                <h3 class="headings">Logout</h3>
            </button>
        </nav>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';

@Component
export default class Navigationbar extends Vue {

    private logout() {
        this.$store.commit('unsubscribe');
        this.$store.commit('deleteToken');
        this.$store.commit('setUsername');
        this.$router.push({path: '/', name: 'login'});
    }
}
</script>

<style scoped>

#wrapper {
    position: relative;
    width: 100vw;
    height: 60px;
    background-color: #5F4D89;
}

#logoutBtn {
    position: relative;
    float: right;
    margin: 5px 10px 10px 5px;
    height: 50px;
    border-radius: 5px;
    width: 10%;
    background-color: #72D9B4;
}


#logoutBtn:hover {
	background-color: #a8f5d9;
}

.headings {
    text-align: center;
    letter-spacing: 0px;
}

</style>
