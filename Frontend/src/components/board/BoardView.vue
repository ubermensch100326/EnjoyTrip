<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { viewBoard, deleteBoard, listComment } from "@/api/board";
import CommentListItem from "@/components/board/item/CommentListItem.vue";

const route = useRoute();
const router = useRouter();

// const boardno = ref(route.params.boardno);
const { boardno } = route.params;

const board = ref({});

onMounted(() => {
  getBoard();
  getCommentList();
});

const getBoard = () => {
  viewBoard(
    boardno,
    ({ data }) => {
      board.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "board-list" });
}

function moveModify() {
  router.push({ name: "board-modify", params: { boardno } });
}

function onDeleteBoard() {
  deleteBoard(
    boardno,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}

// 가능하다면 댓글에도 PageNavigation 컴포넌트 적용시킬 것
const commentList = ref([]);
const param = ref({
  boardno: boardno,
});

const getCommentList = () => {
  listComment(
    param.value,
    ({ data }) => {
      console.log("댓글 요청##############################");
      console.log(data);
      commentList.value = data.commentList;
      console.log("댓글 리스트 테스트#####################");
      console.log(commentList);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <h1>BoardDetail.vue</h1>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ board.boardNo }}. {{ board.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">안효인</span> <br />
                <span class="text-secondary fw-light">
                  {{ board.registerTime }} 조회 : {{ board.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ commentList.length }}</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ board.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteBoard">
              글삭제
            </button>
          </div>
        </div>
      </div>
      <table class="table table-hover">
        <thead>
          <tr class="text-center">
            <th scope="col">댓글 번호</th>
            <th scope="col">댓글 작성자</th>
            <th scope="col">댓글 내용</th>
            <th scope="col">댓글 작성일</th>
            <th scope="col">삭제</th>
            <th scope="col">수정</th>
          </tr>
        </thead>
        <tbody>
          <CommentListItem
            v-for="comment in commentList"
            :key="comment.commentNo"
            :comment="comment"
          ></CommentListItem>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
