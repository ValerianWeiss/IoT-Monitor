<template>
    <div>
        <nav class="mainNavigation">
            <div class="navigationWrapper">
                <ul>
                    <li><a>About me</a></li>
                    <li><a>Showcase</a></li>
                    <li><a>Contact</a></li>
                    <li><a>Blog</a></li>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Component from 'vue-class-component';

export default class Navigationbar extends Vue {

}
</script>

<style scoped>

.mainNavigation{
    width: auto;
    height: 50px;
    background-color: black;
}

.mainNavigation ul{
    list-style-type: none;
    margin: 0;
    padding: 0;
}

.mainNavigation ul li{
    float: left;
}

.navigationWrapper{
    margin: auto;
    width: 70%;
}

</style>
