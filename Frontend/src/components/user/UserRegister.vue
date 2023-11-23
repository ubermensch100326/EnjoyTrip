<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { registerUser } from "@/api/user";
const router = useRouter();

const login = () => {
  router.push({ name: "user-login" });
};

const userData = ref({
  userName: "",
  userId: "",
  userPwd: "",
  checkPwd: "",
  emailId: "",
  emailDomain: 0,
  birthYear: 0,
});

function moveView() {
  router.push({ name: "user-login" });
  location.reload();
}

const currentYear = new Date().getFullYear();
const birthYearRange = ref(
  Array.from({ length: currentYear - 1900 + 1 }, (_, i) => currentYear - i)
);

const register = () => {
  registerUser(
    userData.value,
    (response) => {
      // Assuming the response status is 201 for successful registration
      if (response.status === 201) {
        router.push({ name: "user-login" }); // Redirect to login on successful registration
      } else {
        console.error("Registration failed."); // Handle registration failure
      }
    },
    (error) => console.error(error)
  );
};

const flag = ref(false);
const passwordMatchMessage = ref("");
// // 비밀번호 일치 여부 확인 함수
// const checkPasswordMatch = () => {
//   if (userData.value.userPwd === userData.value.checkPwd) {
//     passwordMatchMessage.value = "비밀번호가 일치합니다.";
//     flag.value = true;
//   } else {
//     passwordMatchMessage.value = "비밀번호가 일치하지 않습니다.";
//     flag.value = false;
//   }
// };
// userData 값 변경 시마다 비밀번호 일치 여부 확인
// watch(
//   () => userData.value.checkPwd,
//   () => {
//     checkPasswordMatch();
//   }
// );

const check = () => {
  if (userData.value.userPwd === "" || userData.value.checkPwd === "") {
    passwordMatchMessage.value = "";
  } else if (userData.value.userPwd === userData.value.checkPwd) {
    passwordMatchMessage.value = "비밀번호가 일치합니다";
    flag.value = true;
  } else {
    passwordMatchMessage.value = "비밀번호가 일치하지 않습니다";
    flag.value = false;
  }
};
</script>

<template>
  <!-- <h1>UserRegister.vue</h1> -->
  <main class="container text-center">
    <div class="register-page">
      <div class="fs-3 fw-bold container">회원가입</div>
      <div style="height: 30px"></div>
      <div class="form">
        <form @submit.prevent="handleRegister">
          <div class="my-2"></div>
          <input
            v-model="userData.userId"
            class="form-control my-2 d-inline-block"
            id="userid"
            name="user_id"
            type="text"
            placeholder="아이디"
            style="width: 202px"
          />
          <br />
          <input
            v-model="userData.userPwd"
            class="form-control my-2 d-inline-block"
            id="userpwd"
            name="user_password"
            type="password"
            @keyup="check"
            placeholder="비밀번호"
            autocomplete="off"
            style="width: 202px"
          />
          <br />
          <input
            v-model="userData.checkPwd"
            class="form-control my-2 d-inline-block"
            id="checkpwd"
            name="checkpwd"
            type="password"
            @keyup="check"
            autocomplete="off"
            placeholder="비밀번호 확인"
            style="width: 202px"
          />
          <!-- 원래 여기는 아이디 입력할 때마다 쓸 수 있는 건지 확인함 -->
          <div id="result-view-id" :class="{ 'text-success': flag, 'text-danger': !flag }">
            {{ passwordMatchMessage || "\u00A0" }}
          </div>
          <div class="input-group my-3 mx-auto" style="width: 350px">
            <input
              v-model="userData.emailId"
              type="text"
              class="form-control"
              id="emailid"
              name="email_id"
              placeholder="이메일 아이디"
              style="width: 151px"
            />
            <span class="input-group-text">@</span>
            <select
              v-model="userData.emailDomain"
              class="form-select"
              id="emaildomain"
              name="email_domain"
              aria-label="이메일 도메인"
              style="width: 151px"
            >
              <option value="0" selected disabled>이메일 도메인</option>
              <option value="ssafy.com">ssafy.com</option>
              <option value="google.com">google.com</option>
              <option value="naver.com">naver.com</option>
              <option value="kakao.com">kakao.com</option>
            </select>
          </div>
          <input
            v-model="userData.userName"
            class="form-control my-2 d-inline-block"
            id="username"
            name="user_name"
            type="text"
            placeholder="이름"
            style="width: 202px"
          />
          <br />
          <select
            v-model="userData.birthYear"
            class="form-control my-2 d-inline-block"
            style="width: 202px"
          >
            <option value="0" selected disabled>출생 연도</option>
            <option v-for="year in birthYearRange" :key="year" :value="year">{{ year }}</option>
          </select>
          <br />
          <!-- <div class="my-5"></div> -->
          <div style="height: 30px"></div>
          <button id="btn-tologin" class="btn btn-secondary me-3" type="button" @click="login">
            로그인
          </button>
          <button class="btn btn-secondary ms-3" type="button" id="btn-register" @click="register">
            회원가입
          </button>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped></style>
