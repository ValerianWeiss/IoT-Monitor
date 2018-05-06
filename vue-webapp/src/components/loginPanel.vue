<template>
    <div id="loginWrapper">
        <form id="loginForm" autocomplete="off">
            <input class="loginInput" type="text" v-model="username" placeholder="Username"/>
            <input class="loginInput" type="password" v-model="password" placeholder="Password"/>
            <button id="loginBtn" class="btn" type="button" @click="login">Login</button>
            <button id="registerBtn" class="btn" type="button" @click="register">Register</button>
        </form>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import User from './../classes/User';
import WebSocket from './../classes/WebSocket'

@Component
export default class LoginPanel extends Vue {
    
    private username: string;
    private password: string;
    private webSocket: WebSocket;

    constructor(){
        super();
        this.username = '';
        this.password = '';
        this.webSocket = new WebSocket();
    }

    login()
    {
       
    }

    register()
    {

    }
}
</script>

<style scoped>
#loginWrapper{
    position: absolute;
    margin: 20vh 50vw;
    transform: translateX(-50%);
    padding: 50px 30px 50px 30px;
    width: 250px;
}

.loginInput{
    width: 100%;
    margin: 0;
    padding: 5px 0 2px 0;
    background: 0;
    border: 0;
    border-bottom: 1px solid #000;
    outline: 0;
    font-style: italic;
    font-size: 12px;
    font-weight: 400;
    letter-spacing: 1px;
    margin-bottom: 5px;
    outline: 0;
}

.loginInput::placeholder{
    color: dimgray;
}
</style>

