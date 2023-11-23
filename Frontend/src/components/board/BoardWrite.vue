<script setup>
import BoardFormItem from "@/components/board/item/BoardFormItem.vue";
</script>

<template>
  <!-- <h1>BoardWrite.vue</h1> -->
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="fs-3 fw-bold container">후기 작성</div>
      <div style="height: 30px"></div>
      <BoardFormItem :boardno="$route.params" type="register" />
    </div>
  </div>
</template>

<style scoped></style>
