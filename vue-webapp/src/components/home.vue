<template>
  <div id="home">
       <navigationbar></navigationbar>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';
import navigationbar from './navigationbar.vue';

@Component({
    components: {
        navigationbar,
    }
})
export default class Home extends Vue {

}
</script>
