<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useNavigationBarStore } from "@/stores/navigation-bar";

const { changeNavigationBarState } = useNavigationBarStore();

const login = () => {
  changeNavigationBarState();
};

const loginFailMessage = ref("아이디나 비밀번호를 확인하세요");
</script>

<template>
  <h1>UserLogin.vue</h1>
  <main class="container text-center">
    <div class="login-page">
      <div class="fs-3 fw-bold container">로그인</div>
      <br />
      <br />
      <div class="form">
        <form>
          <!-- 로그인에 실패했을 경우에 나오는 메시지 -->
          <div class="text-danger my-2">{{ loginFailMessage }}</div>

          <input
            class="form-control my-2 d-inline-block"
            id="userid"
            name="user_id"
            type="text"
            placeholder="아이디"
            style="width: 202px"
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
          />
          <br />
          <input
            class="form-check-input mb-2"
            type="checkbox"
            value="ok"
            id="saveid"
            name="saveid"
          />
          <label class="form-check-label" for="saveid"> 아이디 저장 </label>
          <div class="my-5"></div>

          <button id="btn-toregister" class="btn btn-secondary me-3" type="button">회원가입</button>

          <button class="btn btn-secondary ms-3" type="button" id="btn-login" @click="login">
            로그인
          </button>
        </form>
      </div>
    </div>
  </main>
</template>

<style scoped></style>
