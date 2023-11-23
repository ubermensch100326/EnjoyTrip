<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listBoard } from "@/api/board.js";

import OptionSelect from "@/components/common/OptionSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const optionList = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "board_no" },
  { text: "제목", value: "title" },
  { text: "작성자아이디", value: "user_id" },
]);

const boardList = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_BOARD_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_BOARD_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getBoardList();
});

const changeKey = (val) => {
  console.log("검색 조건 변경 : " + val);
  param.value.key = val;
};

const getBoardList = () => {
  listBoard(
    param.value,
    ({ data }) => {
      console.log(data);
      boardList.value = data.boardList;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getBoardList();
};

const moveWrite = () => {
  router.push({ name: "board-write" });
};

const moveList = () => {
  location.reload();
};
</script>

<template>
  <!-- <h1>BoardList.vue</h1> -->
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="fs-3 fw-bold container">여행 후기</div>
      <div style="height: 30px"></div>
      <div class="col-lg-10">
        <div class="row align-self-center my-2 alert-secondary alert">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-secondary" @click="moveList">목록</button>
          </div>
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-secondary" @click="moveWrite">글쓰기</button>
          </div>
          <div class="col-md-5 offset-3">
            <form class="d-flex">
              <OptionSelect :optionList="optionList" @onKeySelect="changeKey" />
              <!-- 왜 엔터 keyup.enter이 안 먹힐까 -->
              <div class="input-group ms-1">
                <input type="text" class="form-control" v-model="param.word" placeholder="검색어" />
                <button class="btn btn-secondary" type="button" @click="getBoardList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <div style="height: 20px"></div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col" style="width: 10%">글번호</th>
              <th scope="col" style="width: 35%">제목</th>
              <th scope="col" style="width: 15%">작성자</th>
              <th scope="col" style="width: 10%">조회수</th>
              <th scope="col" style="width: 20%">작성시간</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="board in boardList"
              :key="board.boardNo"
              :board="board"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <div style="height: 30px"></div>
      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></PageNavigation>
    </div>
  </div>
</template>

<style scoped></style>
