<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registerBoard, toModifyBoard, modifyBoard } from "@/api/board";
import { useUserStore } from "@/stores/user";
import TheTestView from "../../../views/TheTestViewWrite.vue";

const { userInfo } = useUserStore();

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String, boardno: Number });

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

const childComponentRef = ref(null);

onMounted(() => {
  childComponentRef.value = ref("childComponent");
});

function onSubmit() {
  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "register" ? writeArticle() : updateArticle();
  }
}

const propsno = ref(0);

function writeArticle() {
  console.log("글등록하자!!", board.value);
  // 리턴 값으로 글 번호 들어옴
  registerBoard(
    board.value,
    (response) => {
      console.log(response.data);
      // let msg = "글등록 처리시 문제 발생했습니다.";
      // if (response.status == 201) {
      //     // msg = "글등록이 완료되었습니다.";
      //     // alert(msg);
      //     moveList();
      // }
      propsno.value = response.data;
      console.log("propsno.value!!!!!!!!!!!!" + propsno.value);
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(board.value.boardNo + "번글 수정하자!!", board.value);
  modifyBoard(
    board.value,
    (response) => {
      // let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) {
        // msg = "글정보 수정이 완료되었습니다.";
        // alert(msg);
        moveView();
        // router.push({ name: "board-view" });
        // router.push(`/board/view/${board.value.boardNo}`);
      }
    },
    (error) => console.log(error)
  );
}

function moveView() {
  router.push({ name: "board-view", params: props.boardno });
}

function moveList() {
  router.push({ name: "board-list" });
}

const selectedAttraction = ref({});
</script>

<template>
  <!-- <h1>BoardFormItem.vue</h1> -->
  <the-test-view
    ref="childComponent"
    :selectedAttraction="selectedAttraction"
    :boardno="propsno"
  ></the-test-view>

  <div class="col-lg-10">
    <form @submit.prevent="onSubmit" class="row">
      <div style="height: 70px"></div>
      <div class="fs-5 fw-bold container mb-3">후기</div>
      <div class="mb-3">
        <label for="userid" class="form-label"></label>
        <input
          type="text"
          class="form-control"
          :value="userInfo.userId"
          disabled="true"
          placeholder="작성자"
        />
      </div>
      <div class="mb-3">
        <label for="title" class="form-label"></label>
        <input type="text" class="form-control" v-model="board.title" placeholder="제목" />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label"> </label>
        <textarea
          class="form-control"
          v-model="board.content"
          rows="10"
          placeholder="내용"
        ></textarea>
      </div>
      <div style="height: 30px"></div>
      <div class="col text-center">
        <button
          type="button"
          class="row-3 float-right btn btn-secondary mb-3 mx-3 rounded-1 border-0"
          @click="moveList"
        >
          목록
        </button>
        <button
          type="submit"
          class="row-3 offset-9 float-right btn btn-secondary mb-3 mx-3 rounded-1 border-0"
          v-if="type === 'register'"
        >
          작성
        </button>
        <button
          type="row-3 offset-9 submit"
          class="float-right btn btn-secondary mb-3 mx-3 rounded-1 border-0"
          v-else
        >
          수정
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
