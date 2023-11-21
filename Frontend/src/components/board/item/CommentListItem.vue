<script setup>
defineProps({ comment: Object });
</script>

<template>
  <h1>CommentListItem.vue</h1>
  <tr class="text-center">
    <th scope="row">{{ comment.commentNo }}</th>
    <td class="text-start">
      <!-- 삭제, 수정용 -->
      <!-- <router-link
        :to="{ name: 'board-view', params: { commentno: board.boardNo } }"
        class="board-title link-dark"
      >
        {{ board.title }}
      </router-link> -->
    </td>
    <td>{{ comment.userName }}</td>
    <td>{{ comment.content }}</td>
    <td>{{ comment.registerTime }}</td>
  </tr>
</template>

<style scoped></style>
