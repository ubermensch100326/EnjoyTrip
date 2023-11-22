<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useNavigationBarStore } from "@/stores/navigation-bar";

const userStore = useUserStore();
let token = sessionStorage.getItem("accessToken");
const router = useRouter();

const { getUserInfo, userInfo, isLogin, isValidToken } = userStore;

onMounted(async (token) => {
  // console.log("토큰 검사!!!!!!!!!!!!!!!!!! " + token);
  // // 토큰 유효성 검증 후 유저 정보 가져오기.
  // if (token) {
  //   getUserInfo(token);
  //   // if (!isLogin || !isValidToken) {
  //   //   console.log("되나??");
  //   //   router.push({ name: "home" });
  //   // }
  // } else {
  //   console.log("MyPage async start with token => " + token);
  // }
  if (userInfo == null) router.push({ name: "home" });
});
</script>

<template>
  <h1>UserMyPage.vue</h1>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">내정보</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="card mt-3 m-auto" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-4">
              <img
                src="https://source.unsplash.com/random/250x250/?food"
                class="img-fluid rounded-start"
                alt="..."
              />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start">
                <ul class="list-group list-group-flush" v-if="userInfo != null">
                  <li class="list-group-item">{{ userInfo.userId }}</li>
                  <li class="list-group-item">{{ userInfo.userName }}</li>
                  <li class="list-group-item">
                    {{ userInfo.emailId + "@" + userInfo.emailDomain }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-outline-secondary mt-2">수정</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
