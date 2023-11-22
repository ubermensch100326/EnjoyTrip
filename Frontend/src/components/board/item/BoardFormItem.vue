<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registerBoard, toModifyBoard, modifyBoard } from "@/api/board";
import { useUserStore } from "@/stores/user";

const { userInfo } = useUserStore();

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const board = ref({
  boardNo: 0,
  title: "",
  content: "",
  userId: userInfo.userId,
  userName: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { boardno } = route.params;
  toModifyBoard(
    boardno,
    ({ data }) => {
      board.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  isUseId.value = true;
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => board.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      titleErrMsg.value = "제목을 확인해 주세요!!!";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => board.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "register" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", board.value);
  registerBoard(
    board.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(board.value.boardNo + "번글 수정하자!!", board.value);
  modifyBoard(
    board.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "board-view" });
      // router.push(`/board/view/${board.value.boardNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "board-list" });
}
</script>

<template>
  <h1>BoardFormItem.vue</h1>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        :value="userInfo.userId"
        disabled="true"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="title" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="board.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="board.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'register'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
