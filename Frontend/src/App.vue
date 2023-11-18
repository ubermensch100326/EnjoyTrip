<script setup>
import TheNavigationbarVue from "./components/layout/TheNavigationbar.vue";
</script>

<template>
  <h1>App.vue</h1>
  <the-navigationbar-vue></the-navigationbar-vue>
  <router-view></router-view>
</template>

<style scoped>
:focus {
  border-color: none !important;
  box-shadow: none !important;
  outline: none !important;
}
</style>
