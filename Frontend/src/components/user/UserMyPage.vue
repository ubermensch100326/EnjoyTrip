<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useNavigationBarStore } from "@/stores/navigation-bar";
import { modifyUserInfo } from "@/api/user";

const userStore = useUserStore();
let token = sessionStorage.getItem("accessToken");
const router = useRouter();

const { getUserInfo, userInfo, isLogin, isValidToken } = userStore;

// onMounted(async (token) => {
//   // console.log("토큰 검사!!!!!!!!!!!!!!!!!! " + token);
//   // // 토큰 유효성 검증 후 유저 정보 가져오기.
//   // if (token) {
//   //   getUserInfo(token);
//   //   // if (!isLogin || !isValidToken) {
//   //   //   console.log("되나??");
//   //   //   router.push({ name: "home" });
//   //   // }
//   // } else {
//   //   console.log("MyPage async start with token => " + token);
//   // }
//   if (userInfo == null) router.push({ name: "home" });
// });

const editedUserInfo = ref({
  userId: "",
  userName: "",
  email: "",
  birthYear: 0,
});

const editing = ref(false);

const editUserInfo = () => {
  editedUserInfo.value.userId = userInfo.userId;
  editedUserInfo.value.userName = userInfo.userName;
  editedUserInfo.value.email = `${userInfo.emailId}@${userInfo.emailDomain}`;
  editedUserInfo.value.birthYear = userInfo.birthYear;
  editing.value = true;
};

const cancelEdit = () => {
  editing.value = false;
};

// 나중에 일부만 리로딩하는 걸로 바꿔볼 것
function moveView() {
  router.push({ name: "user-mypage" });
  location.reload();
}

// API 호출 또는 저장 로직 추가
// editedUserInfo 객체를 이용하여 서버로 변경된 정보 전송
// 성공 시 editing 값을 false로 변경하여 수정 모드 종료
function saveChanges() {
  modifyUserInfo(
    editedUserInfo.value,
    (response) => {
      console.log("editedUserInfo : " + editedUserInfo.value);
      if (response.status == 200) {
        console.log("회원정보 수정 완료!!!!");
        moveView();
      }
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <!-- <h1>UserMyPage.vue</h1> -->
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="fs-3 fw-bold container">내 정보</div>
      <div style="height: 30px"></div>

      <div class="col-lg-10">
        <div class="card m-auto my-2 border-0" style="max-width: 700px">
          <div class="row g-0">
            <div class="col-md-4">
              <img
                src="https://source.unsplash.com/random/250x250/?food"
                class="img-fluid rounded-start"
                alt="..."
              />
            </div>
            <div class="col-md-8">
              <div class="card-body text-start border-0">
                <div v-if="!editing">
                  <ul class="list-group list-group-flush border-0" v-if="userInfo">
                    <li class="list-group-item border-0 my-1">{{ userInfo.userId }}</li>
                    <li class="list-group-item border-0 md-1">{{ userInfo.userName }}</li>
                    <li class="list-group-item border-0 md-1">
                      {{ userInfo.emailId + "@" + userInfo.emailDomain }}
                    </li>
                    <li class="list-group-item border-0 md-1">{{ userInfo.birthYear }}</li>
                  </ul>
                  <button
                    type="button"
                    class="ms-5 me-2 my-5 btn btn-secondary mt-2"
                    @click="editUserInfo"
                  >
                    수정
                  </button>
                </div>
                <div v-else>
                  <form @submit.prevent="saveChanges">
                    <ul class="list-group list-group-flush border-0">
                      <li class="list-group-item border-0">
                        <input
                          v-model="editedUserInfo.userId"
                          disabled="true"
                          class="border-0 rounded-1"
                        />
                      </li>
                      <li class="list-group-item border-0">
                        <input v-model="editedUserInfo.userName" class="border-1 rounded-1" />
                      </li>
                      <li class="list-group-item border-0">
                        <input
                          v-model="editedUserInfo.email"
                          disabled="true"
                          class="border-0 rounded-1"
                        />
                      </li>
                      <li class="list-group-item border-0">
                        <input v-model="editedUserInfo.birthYear" class="border-1 rounded-1" />
                      </li>
                    </ul>
                    <button
                      type="submit"
                      class="ms-5 me-2 my-5 btn btn-secondary mt-2"
                      @click="saveChanges"
                    >
                      저장
                    </button>
                    <button type="button" class="my-5 btn btn-secondary mt-2" @click="cancelEdit">
                      취소
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div></div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
