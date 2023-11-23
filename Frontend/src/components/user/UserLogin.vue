<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useNavigationBarStore } from "@/stores/navigation-bar";

const router = useRouter();
const userStore = useUserStore();

// storeToRefs : 구조 분해 할당을 통해 값을 가져올 때 반응형을 잃지 않도록 해줌
const { isLogin } = storeToRefs(userStore);
const { userLogin, getUserInfo } = userStore;
const { changeNavigationBarState } = useNavigationBarStore();

const loginUser = ref({
  userId: "",
  userPwd: "",
});

const login = async () => {
  console.log("UserLogin.vue : login async 시작");
  // 여기서 await를 안써주면 밑으로 바로 내려가서 getItem을 할 때 null이 뜨니까 반드시 await를 써야 함
  await userLogin(loginUser.value);
  // 위의 userLogin이 정상적으로 처리됐다면 stores/user.js에서 session에 access token과 refresh token이 저장된 상태임
  // 그럼 이제 session에서 얻어오면 된다는 것이므로 아래 코드 수행
  let token = sessionStorage.getItem("accessToken");
  console.log("UserLogin.vue : token => ", token);
  console.log("UserLogin.vue : isLogin => ", isLogin);

  // 로그인 성공했으면 isLogin 이 pinia 안에서 true 로 바뀌고 그 값이 여기서도 변경됨.
  if (isLogin) {
    // 위에서 작업하고 나서 access와 refresh token이 발급된 상태임
    // 그런데 화면에 아직 user 정보를 찍은 상태는 아님
    // 그래서 token 정보를 가지고 userinfo로 가야 함
    getUserInfo(token);
    // changeNavigationBarState();
  }
  router.push("/");
};

const register = () => {
  router.push({ name: "user-register" });
};

const loginFailMessage = ref("아이디나 비밀번호를 확인하세요");
</script>

<template>
  <!-- <h1>UserLogin.vue</h1> -->
  <main class="container text-center">
    <div class="login-page">
      <div class="fs-3 fw-bold container">로그인</div>
      <div style="height: 30px"></div>
      <div class="form">
        <form>
          <!-- 로그인에 실패했을 경우에 나오는 메시지 -->
          <!-- <div class="text-danger my-2">{{ loginFailMessage }}</div> -->

          <input
            class="form-control my-2 d-inline-block"
            id="userid"
            name="user_id"
            type="text"
            placeholder="아이디"
            style="width: 202px"
            v-model="loginUser.userId"
          />
          <br />
          <input
            class="form-control my-2 d-inline-block"
            id="userpwd"
            name="user_password"
            type="password"
            placeholder="비밀번호"
            autocomplete="off"
            style="width: 202px"
            v-model="loginUser.userPwd"
            @keyup.enter="login"
          />
          <br />
          <!-- <input
            class="form-check-input mb-2"
            type="checkbox"
            value="ok"
            id="saveid"
            name="saveid"
          /> -->
          <!-- <label class="form-check-label" for="saveid"> 아이디 저장 </label> -->
          <!-- <div class="my-5"></div> -->
          <div style="height: 30px"></div>
          <button
            id="btn-toregister"
            class="btn btn-secondary me-3"
            type="button"
            @click="register"
          >
            회원가입
          </button>

          <button class="btn btn-secondary ms-3" type="button" id="btn-login" @click="login">
            로그인
          </button>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped></style>
