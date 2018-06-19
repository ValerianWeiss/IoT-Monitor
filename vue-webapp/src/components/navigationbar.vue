<template>
    <div id="navigationbar">
        <nav id="wrapper">
            <button class="btn" id="logoutBtn" @click="logout">
                <p class="headings">Logout</p>
            </button>
        </nav>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';

@Component
export default class Navigationbar extends Vue {

    private logout() {
        this.$store.commit('deleteToken');
        this.$router.push('/');
    }
}
</script>

<style scoped>

#wrapper {
    width: 100vw;
    height: 60px;
    background-color: rgb(129, 165, 231);
}

#logoutBtn {
    position: relative;
    float: right;
    margin: 5px 10px 5px 5px;
    height: 50px;
    border-radius: 20px;
    width: 10%;
    background-color:azure;
}

.headings {
    text-align: center;
    letter-spacing: 0px;
}

</style>
