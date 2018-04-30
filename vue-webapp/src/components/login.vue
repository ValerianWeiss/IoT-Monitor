<template>
	<div>
		<loginPanel></loginPanel>
		<sideheading :heading="heading"></sideheading>
	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import sideheading from './sideheading.vue';
import loginPanel from './loginPanel.vue';

@Component({
	components:{
		sideheading,
		loginPanel,
	}
})
export default class Login extends Vue {
	heading: string = 'Login';
}
</script>




