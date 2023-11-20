<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const login = () => {
  router.push({ name: "user-login" });
};
</script>

<template>
  <h1>UserRegister.vue</h1>
  <main class="container text-center">
    <div class="register-page">
      <div class="fs-3 fw-bold container">회원가입</div>
      <br />
      <br />
      <div class="form">
        <form name="form-register" class="form-register" id="form-register" method="post">
          <div class="my-2"></div>
          <input
            class="form-control my-2 d-inline-block"
            id="username"
            name="user_name"
            type="text"
            placeholder="이름"
            style="width: 202px"
          />
          <br />
          <input
            class="form-control my-2 d-inline-block"
            id="userid"
            name="user_id"
            type="text"
            placeholder="아이디"
            style="width: 202px"
          />
          <br />
          <div id="result-view-id" class="mb-2"><br /></div>
          <input
            class="form-control my-2 d-inline-block"
            id="userpwd"
            name="user_password"
            type="password"
            placeholder="비밀번호"
            autocomplete="off"
            style="width: 202px"
          />
          <br />
          <input
            class="form-control my-2 d-inline-block"
            id="checkpwd"
            name="checkpwd"
            type="password"
            autocomplete="off"
            placeholder="비밀번호 확인"
            style="width: 202px"
          />
          <br />
          <div id="result-view-pw" class="mb-2"><br /></div>
          <div class="input-group my-3 mx-auto" style="width: 350px">
            <input
              type="text"
              class="form-control"
              id="emailid"
              name="email_id"
              placeholder="이메일 아이디"
              style="width: 151px"
            />
            <span class="input-group-text">@</span>
            <select
              class="form-select"
              id="emaildomain"
              name="email_domain"
              aria-label="이메일 도메인"
              style="width: 151px"
            >
              <option selected>선택</option>
              <option value="ssafy.com">ssafy.com</option>
              <option value="google.com">google.com</option>
              <option value="naver.com">naver.com</option>
              <option value="kakao.com">kakao.com</option>
            </select>
          </div>
          <div class="my-5"></div>
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
