<template>
    <div id="loginWrapper">
        <form id="loginForm" autocomplete="off">
            <input class="formInput" type="text" v-model="username" placeholder="Username"/>
            <input v-if="!loginContext" class="formInput" type="text" v-model="email" placeholder="E-mail"/>
            <input class="formInput" type="password" v-model="password" placeholder="Password"/>
            <input v-if="!loginContext" class="formInput" type="password" v-model="passwordRepeated" placeholder="Repeat password"/>
            <button v-if="loginContext" id="loginBtn" class="btn" type="button" @click="onLogin">Login</button>
            <button id="registerBtn" class="btn" type="button" @click="onRegister">Register</button>
        </form>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import User from './../classes/User';
import Axios,{ AxiosResponse } from 'axios';
import LoginRequest from '../classes/communication/LoginRequest';
import RegisterRequest from '../classes/communication/RegisterRequest';
import ResponseMessage from '../classes/communication/ResponseMessage';
import Router from '../router';
import Config from '../appConfig.json';
import Store from '../store';
import { String } from 'typescript-string-operations';

@Component
export default class LoginPanel extends Vue {
    
    private username: string = String.Empty;
    private password: string = String.Empty;
    private passwordRepeated: string = String.Empty;
    private email: string = String.Empty;
    private loginContext: boolean = true;

    private userUrl: string = Config.backendUrl + '/user';


    private onLogin() : void {
        if(this.username != String.Empty && this.password != String.Empty) {
            Axios.put(this.userUrl, new LoginRequest(this.username, this.password)).
                then(this.login);
        }
    }

    private onRegister() : void {
        if(this.loginContext) {
            this.loginContext = !this.loginContext;
            return;
        }
        
        //TODO Email validation

        if(this.password == this.passwordRepeated) {
            Axios.post(this.userUrl, new RegisterRequest(this.username, this.password, 
                                                                this.passwordRepeated, this.email)).
                then(this.login);
        }
    }
    
    private login(response : AxiosResponse<ResponseMessage>) : void {
        if(response.data.success) {
            try {
                let currentUser: User = new User(response.data.payload.payload,
                                                this.username);
                Store.commit('setUser', currentUser);
                Router.push("home");
            } catch (e) {
                throw new Error("Invalid response format");
            }
        } else {
            if(response.data.cause != undefined) {
                console.log(response.data.cause.errorMessage + " " + 
                            response.data.cause.errorCode);
            }
        }
    }
}
</script>

<style scoped>
#loginWrapper {
    position: absolute;
    margin: 20vh 50vw;
    transform: translateX(-50%);
    padding: 50px 30px 50px 30px;
    width: 250px;
}

.formInput {
    width: 100%;
    margin: 0;
    padding: 5px 0 2px 0;
    background: 0;
    border: 0;
    border-bottom: 1px solid #000;
    outline: 0;
    font-size: 12px;
    font-weight: 400;
    letter-spacing: 1px;
    margin-bottom: 8px;
    outline: 0;
}

.formInput::placeholder{
    color: dimgray;
    font-style: italic;
}

.btn {
    margin: 10px 0 0 0;
    width: 100%;
    height: 22px;        
}
</style>

