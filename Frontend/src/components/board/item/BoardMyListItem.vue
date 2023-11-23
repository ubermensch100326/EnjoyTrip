<script setup>
defineProps({ board: Object });
</script>

<template>
  <!-- <h1>BOARDMYLISTITEM</h1> -->
  <tr class="text-center">
    <th scope="row">{{ board.boardNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'board-view', params: { boardno: board.boardNo } }"
        class="board-title link-dark"
      >
        {{ board.title }}
      </router-link>
    </td>
    <td>{{ board.userName }}</td>
    <td>{{ board.hit }}</td>
    <td>{{ board.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
