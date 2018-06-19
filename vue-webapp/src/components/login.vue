<template>
	<div id="login">
		<loginPanel></loginPanel>
		<div id="wrapper">
			<div id="trapezoid"></div>
			<div id="triangle"></div>
		</div>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import loginPanel from './loginPanel.vue';
import { over } from 'stompjs';

@Component({
	components: {
		loginPanel,
	}
})
export default class Login extends Vue {

}
</script>

<style scoped>

#wrapper {
	position: absolute;
	overflow: hidden;
	height: 80vh;
	width: 60vw;
	margin: 20vh 0 0 40vw;
	z-index: -1;
}

#trapezoid {
	position: relative;
	z-index: 1;
	left: -45%;
	width: 300%;
	height: 15vw;
	background-color: #72D9B4;
	transform: rotate(-45deg);
}

#triangle {
	position: relative;
	z-index: 1;
	left: -30%;
	width: 300%;
	height: 30vw;
	background-color: #5F4D89;
	transform: rotate(-45deg);
}
</style>
