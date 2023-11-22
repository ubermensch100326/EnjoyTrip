<script setup>
import { ref } from "vue";

import { deleteComment, modifyComment } from "@/api/board";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({ comment: Object });

const param = ref({
  commentno: props.comment.commentNo,
});

function moveView() {
  router.push({ name: "board-view", params: { boardno: props.comment.boardNo } });
  location.reload();
}

function onDeleteComment() {
  deleteComment(
    param.value,
    (response) => {
      if (response.status == 200) {
        console.log("댓글 삭제 완료!!!!");
        moveView();
      }
    },
    (error) => {
      console.error(error);
    }
  );
}

const editing = ref(false);

function editComment() {
  editing.value = true;
}

// const editedContent = ref(props.comment.content);

const editParam = ref({
  commentno: props.comment.commentNo,
  content: props.comment.content,
});

function saveComment() {
  modifyComment(
    editParam.value,
    (response) => {
      console.log("editParam : " + editParam.value);
      if (response.status == 200) {
        console.log("댓글 수정 완료!!!!");
        moveView();
      }
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <h1>CommentListItem.vue</h1>
  <tr class="text-center">
    <td class="text-start">
      <!-- 삭제, 수정용 -->
      <!-- <router-link
        :to="{ name: 'board-view', params: { commentno: board.boardNo } }"
        class="board-title link-dark"
      >
        {{ board.title }}
      </router-link> -->
      {{ comment.commentNo }}
    </td>
    <td>{{ comment.userId }}</td>
    <td>
      <div v-if="!editing">{{ comment.content }}</div>
      <textarea v-if="editing" v-model="editParam.content" rows="1"></textarea>
    </td>
    <td>{{ comment.registerTime }}</td>
    <td>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteComment">
        댓글삭제
      </button>
    </td>
    <td>
      <button
        v-if="!editing"
        type="button"
        class="btn btn-outline-primary ms-1"
        @click="editComment"
      >
        댓글수정
      </button>
      <button
        v-if="editing"
        type="button"
        class="btn btn-outline-success ms-1"
        @click="saveComment"
      >
        댓글저장
      </button>
    </td>
  </tr>
</template>

<style scoped></style>
