<script setup>
defineProps({ board: Object });
</script>

<template>
  <!-- <h1>BOARDMYLISTITEM</h1> -->
  <tr class="text-center">
    <th scope="row" class="rounded-0">{{ board.boardNo }}</th>
    <td class="text-start rounded-0">
      <router-link
        :to="{ name: 'board-view', params: { boardno: board.boardNo } }"
        class="board-title link-dark"
      >
        {{ board.title }}
      </router-link>
    </td>
    <td class="rounded-0">{{ board.userName }}</td>
    <td class="rounded-0">{{ board.hit }}</td>
    <td class="rounded-0">{{ board.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
