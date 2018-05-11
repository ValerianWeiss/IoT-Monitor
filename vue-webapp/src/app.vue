<template>
	<div>
		<sideheading></sideheading>
    	<router-view/>
  	</div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import sideheading from './components/sideheading.vue';

@Component({
	components: {
		sideheading
	}
})
export default class App extends Vue {
	
	constructor() {
		super();
	}
}
</script>
