<template>
    <div id="loginWrapper">
        <form id="loginForm" autocomplete="off">
            <input class="loginInput" type="text" v-model="p_username" placeholder="p_Username"/>
            <input class="loginInput" type="password" v-model="p_password" placeholde_r="Password"/>
            <button id="loginBtn" class="btn" type="button" @click="login">Login</button>
            <button id="registerBtn" class="btn" type="button" @click="register">Register</button>
        </form>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import User from './../classes/User';
import WebSocket from './../classes/WebSocket';
import Axios,{ AxiosResponse } from 'axios';
import LoginMessage from '../classes/communication/LoginMessage';
import ResponseMessage from '../classes/communication/ResponseMessage';
import Router from '../router';
import Config from '../appConfig.json';

@Component
export default class LoginPanel extends Vue {
    
    private p_username: string = '';
    private p_password: string = '';


    private login() : void {
        if(this.p_username != undefined && this.p_password != undefined) {
            Axios.put(Config.backendUrl + 'user', new LoginMessage(this.p_username, this.p_password)).
                then(function(response : AxiosResponse<ResponseMessage>) : void {
                    if(response.data.success) {
                        Router.push("home");
                    }
                });
        }
    }   

    private register() : void {

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

