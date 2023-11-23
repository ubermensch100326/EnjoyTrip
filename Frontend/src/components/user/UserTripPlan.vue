<script setup>
import { onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { userBoardList } from "../../api/board";

const User = useUserStore();

const UserInfo = User.userInfo;

// userId, userName, userPwd, refreshToken 등등

/** 이 페이지에 들어오면 해당 사용자의 여행 계획을 로드 해야 함. */
const clickBut = () => {
  /** Axios 요청을 보내서 Board와 Board_detail 정보를 가져 오도록 함. */
  userBoardList(
    UserInfo,
    ({ data }) => {
      data.forEach((element) => {
        console.log(element.board_no);
      });
    },
    (err) => {
      console.log(err);
    }
  );
};
</script>

<template>
  <!-- <h1>UserTripPlan.vue</h1> -->

  <router-view></router-view>

  <!-- <div class="container d-flex flex-column">
        <button @click="clickBut">버튼입네데</button>
        <div class="d-flex justify-content-around mt-5 mb-5">
            <div>
                <div class="card p-5 bg-light" style="width: 18rem">
                    <img src="..." class="card-img-top" alt="..." />
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">
                            Some quick example text to build on the card title and make up the bulk
                            of the card's content.
                        </p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div>
                <div class="card p-5" style="width: 18rem">
                    <img src="..." class="card-img-top" alt="..." />
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">
                            Some quick example text to build on the card title and make up the bulk
                            of the card's content.
                        </p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-around">
            <div>
                <div class="card p-5" style="width: 18rem">
                    <img src="..." class="card-img-top" alt="..." />
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">
                            Some quick example text to build on the card title and make up the bulk
                            of the card's content.
                        </p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div>
                <div class="card p-5" style="width: 18rem">
                    <img src="..." class="card-img-top" alt="..." />
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">
                            Some quick example text to build on the card title and make up the bulk
                            of the card's content.
                        </p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>
        </div>
    </div> -->
</template>

<style scoped></style>
