<template>
    <div id="loginPanel">
        <form id="loginForm" autocomplete="off">
            <input class="formInput" type="text" v-model="username" placeholder="Username"/>
            <p class="hintText">{{userHintMessage}}</p>
            <input class="formInput passInput" type="password" v-model="password" placeholder="Password"/>
            <p class="hintText">{{pwHintMessage}}</p>
            <input v-if="!loginContext" class="formInput passInput" type="password" v-model="passwordRepeated" placeholder="Repeat password"/>
            <p v-if="!loginContext" class="hintText">{{pwHintMessage}}</p>
            <button v-if="loginContext" class="btn" type="button" @click="onLogin">Login</button>
            <button class="btn" type="button" @click="onRegister">Register</button>
            <button v-if="!loginContext" class="btn" type="button"
                    @click="loginContext = !loginContext;
                            pwHintMessage = '';
                            userHintMessage = ''">
                    <span class="doubleArrow">&#171; </span>Back to Login
            </button>
        </form>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Axios,{ AxiosResponse } from 'axios';
import LoginRequest from '../classes/communication/LoginRequest';
import RegisterRequest from '../classes/communication/RegisterRequest';
import ResponseMessage from '../classes/communication/ResponseMessage';
import Config from '../appConfig.json';
import { String } from 'typescript-string-operations';
import { ErrorCode } from '../classes/communication/Error';

@Component
export default class LoginPanel extends Vue {
    
    private username: string = String.Empty;
    private password: string = String.Empty;
    private passwordRepeated: string = String.Empty;
    private email: string = String.Empty;
    private loginContext: boolean = true;
    private minPasswordLength = 6;
    private pwHintMessage: string = String.Empty;
    private userHintMessage: string = String.Empty;

    private userUrl: string = Config.backendAuthUrl + '/user';


    private onLogin() : void {
        if(this.username != String.Empty && this.password != String.Empty) {
            Axios.put(this.userUrl, new LoginRequest(this.username, this.password)).
                then(this.login);
        }
    }

    private onRegister() : void {
       
        if(this.loginContext) {
            this.loginContext = !this.loginContext;
            this.pwHintMessage = String.Empty;
            this.userHintMessage = String.Empty;
            return;
        }
        
        let hint: string = String.Empty;

        if(this.password.length >= this.minPasswordLength && 
                this.password == this.passwordRepeated) {
            Axios.post(this.userUrl, new RegisterRequest(this.username, this.password, 
                                                                this.passwordRepeated)).
                then(this.login).catch(errror => {
                    hint = 'Login server not available'
                    console.log(errror);
                });
        } else {
            if(this.password.length < this.minPasswordLength) {
                hint = 'Password must have at least 6 digest';
            } else if(this.password != this.passwordRepeated) {
                hint = 'Passwords are not equal';
            }
        } 
        this.pwHintMessage = hint;
        this.userHintMessage = String.Empty;
    }
    
    private login(response : AxiosResponse<ResponseMessage>) : void {
        if(response.data.success) {
            try {
                localStorage.setItem(Config.tokenEntity, response.data.payload);
                this.$router.push('/home');
            } catch (e) {
                throw new Error('Invalid response format' + e);
            }
        } else {
            if(response.data.errorCause != undefined) {
                console.log(response.data.errorCause.errorMessage + " " + 
                            response.data.errorCause.errorCode);

                let errorCode = response.data.errorCause.errorCode; 
                                           
                if(errorCode == ErrorCode.usernameAlreadyTaken) {
                    this.userHintMessage = 'Username already taken';
                } else {
                    let msg = 'Password or username are incorrect';
                    this.pwHintMessage = msg;
                    this.userHintMessage = msg;
                }
            } else {
               console.error('unknown error code');
            }
        }
    }
}
</script>

<style scoped>
#loginPanel {
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
    font-size: 12px;
    font-weight: 400;
    letter-spacing: 1px;
    outline: 0;
}

.passInput {
    margin-bottom: 0px;
}

.formInput::placeholder {
    color: dimgray;
    font-style: italic;
}

.btn {
    margin: 10px 0 0 0;
    width: 100%;
    height: 22px;  
    font-weight: 600;     
}

.hintText {
    height: 8px;
    font-weight: 400;
    font-family: Arial, Helvetica, sans-serif;
    position: relative;
    margin: 2px 0 0 0;
    font-size: 8px;
    color: rgb(170, 2, 2);
    font-style: italic;
}
</style>

