<script setup>
import { ref } from "vue";
const msg = ref("테스트용 페이지");
</script>

<template>
  <h1>TheHomeView.vue</h1>
  <div class="mb-3">
    <label for="exampleFormControlTextarea1" class="form-label">Example textarea</label>
    <textarea
      class="form-control"
      id="exampleFormControlTextarea1"
      rows="3"
      v-model="msg"
    ></textarea>
    {{ msg }}
  </div>
</template>
