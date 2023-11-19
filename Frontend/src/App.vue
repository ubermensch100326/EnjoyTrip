<script setup>
import TheNavigationbarVue from "./components/layout/TheNavigationbar.vue";
import TheFooter from "./components/layout/TheFooter.vue";
</script>

<template>
  <br />
  <br />
  <br />
  <h1>App.vue</h1>
  <div id="wrapper">
    <the-navigationbar-vue></the-navigationbar-vue>
    <router-view></router-view>
  </div>
  <the-footer></the-footer>
</template>

<style scoped>
:focus {
  border-color: none !important;
  box-shadow: none !important;
  outline: none !important;
}

#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: (footer높이);
}

the-footer {
  height: (footer높이);
  position: relative;
  transform: translateY(-100%);
}
</style>
