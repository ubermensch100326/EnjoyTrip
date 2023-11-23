<script setup>
import TheNavigationbarVue from "@/components/layout/TheNavigationbar.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <!-- <h1>App.vue</h1> -->
  <div id="wrapper">
    <the-navigationbar-vue></the-navigationbar-vue>

    <div style="height: 150px"></div>
    <router-view></router-view>
    <div style="height: 70px"></div>
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
  padding-bottom: 10px;
}

the-footer {
  height: 10px;
  position: relative;
  transform: translateY(-100%);
}
</style>

<style>
* {
  font-family: "JUA";
  font-size: 18px;
  border-radius: 10px;
}

@font-face {
  font-family: "JUA";
  src: url("@/assets/fonts/JUA.ttf");
}

@font-face {
  font-family: "D2Coding";
  src: url("@/assets/fonts/D2Coding.ttf");
}

input[type="password"] {
  font-family: "D2Coding";
}

input[type="password"]::placeholder {
  font-family: "JUA";
}
</style>
