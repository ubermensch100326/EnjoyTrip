<script setup>
// map
import { ref } from "vue";
const selectedAttraction = ref({});
import TheTestView from "@/views/TheTestView.vue";

import { onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { viewBoard, deleteBoard, listComment, registerComment } from "@/api/board";
import CommentListItem from "@/components/board/item/CommentListItem.vue";
import { useUserStore } from "@/stores/user";

const { userInfo } = useUserStore();
const userId = userInfo?.userId;

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

const comment = ref({
    boardNo: boardno,
    userId: userId,
    content: "",
});

function moveView() {
    // router.push({ name: "board-view", params: boardno });
    // location.reload();
    router.push({ name: "board" });
}

const submitComment = () => {
    // API 호출 또는 저장 로직 추가
    // commentAuthor.value와 commentContent.value를 이용하여 댓글 생성 API 호출
    // 호출 성공 시 commentList 업데이트
    // (여기에 API 호출 및 로직 추가 필요)
    registerComment(
        comment.value,
        (response) => {
            // let msg = "댓글등록 처리시 문제 발생했습니다.";
            // if (response.status == 201) msg = "댓글등록이 완료되었습니다.";
            // alert(msg);
            moveView();
        },
        (error) => console.error(error)
    );
    moveView();
    // getCommentList();
};
</script>

<template>
    <!-- <h1>BoardDetail.vue</h1> -->
    <the-test-view
        :selectedAttraction="selectedAttraction"
        :boardno="boardno"
        :board="board"
    ></the-test-view>

    <div class="container text-center">
        <div class="row justify-content-center">
            <div style="height: 70px"></div>
            <div class="fs-5 fw-bold container mb-3">후기</div>
            <div class="mb-3">
                {{ board.content }}
            </div>
            <div class="col-lg-10">
                <!-- start -->

                <!-- <div class="col-lg-10 text-start">
                    <div class="row my-2">
                        <h2 class="text-secondary px-5">
                            {{ board.boardNo }}. {{ board.subject }}
                        </h2>
                    </div>
                    <div class="row">
                        <div class="col-md-8">
                            <div class="clearfix align-content-center">
                                <img
                                    class="avatar me-2 float-md-start bg-light p-2"
                                    src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                                />
                                <p>
                                    <span class="fw-bold">{{ board.userId }}</span> <br />
                                    <span class="text-secondary fw-light">
                                        {{ board.registerTime }} 조회 : {{ board.hit }}
                                    </span>
                                </p>
                            </div>
                        </div>
                        <div class="col-md-4 align-self-center text-end">
                            댓글 : {{ commentList.length }}
                        </div>
                        <div class="divider mb-3"></div>
                        <div class="text-secondary">
                            {{ board.content }}
                        </div>
                        <div class="divider mt-3 mb-3"></div>
                        <div class="d-flex justify-content-end">
                            <button
                                type="button"
                                class="btn btn-outline-primary mb-3"
                                @click="moveList"
                            >
                                글목록
                            </button>
                            <button
                                v-if="board.userId === userId"
                                type="button"
                                class="btn btn-outline-success mb-3 ms-1"
                                @click="moveModify"
                            >
                                글수정
                            </button>
                            <button
                                v-if="board.userId === userId"
                                type="button"
                                class="btn btn-outline-danger mb-3 ms-1"
                                @click="onDeleteBoard"
                            >
                                글삭제
                            </button>
                        </div>
                    </div>
                </div> -->

                <!-- end -->

                <div>
                    <div style="height: 70px"></div>
                    <table class="table table-hover table-borderless">
                        <thead>
                            <tr class="text-center">
                                <!-- <th scope="col">댓글 번호</th> -->
                                <th scope="col" style="width: 10%">댓글 작성자</th>
                                <th scope="col" style="width: 35%">댓글 내용</th>
                                <th scope="col" style="width: 15%">댓글 작성시간</th>
                                <th scope="col" style="width: 10%">수정</th>
                                <th scope="col" style="width: 10%">삭제</th>
                            </tr>
                        </thead>
                        <tbody>
                            <CommentListItem
                                v-for="comment in commentList"
                                :key="comment.commentNo"
                                :comment="comment"
                                :userId="userId"
                            >
                            </CommentListItem>
                        </tbody>
                    </table>
                </div>
                <div style="height: 70px"></div>
                <!-- 댓글 작성 폼 -->
                <div class="mt-3" v-if="userId">
                    <div class="fs-5 fw-bold container mb-3">댓글</div>
                    <div class="mb-3">
                        <label for="commentAuthor" class="form-label">댓글 작성자</label>
                        <input
                            type="text"
                            class="form-control"
                            id="commentAuthor"
                            :value="userId"
                            disabled="true"
                        />
                    </div>
                    <div class="mb-3">
                        <label for="commentContent" class="form-label">댓글 내용</label>
                        <textarea
                            class="form-control"
                            id="commentContent"
                            rows="3"
                            v-model="comment.content"
                        ></textarea>
                    </div>
                    <div style="height: 30px"></div>
                    <div class="col text-center"></div>
                    <button
                        type="button"
                        class="row-3 float-right btn btn-secondary mb-3 mx-3 rounded-1 border-"
                        @click="submitComment"
                    >
                        작성
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
