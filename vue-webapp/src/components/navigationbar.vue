<template>
    <div id="navigationbar">
        <nav id="wrapper">
            <div id="logoutBtn" @click="logout">
                <p>Log out</p>
            </div>
        </nav>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class Navigationbar extends Vue {

    private logout() {
        this.$store.commit('deleteToken')
    }
}
</script>

<style scoped>

#wrapper {
    width: 100%;
    height: 40px;
    background-color: rgb(129, 165, 231);
}

#logoutBtn {
    position: relative;
    float: right;
    height: 100%;
    width: 15%;
    background-color: burlywood;
}

</style>
