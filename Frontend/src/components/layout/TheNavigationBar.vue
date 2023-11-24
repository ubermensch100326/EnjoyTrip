<script setup>
import { useNavigationBarStore } from "@/stores/navigation-bar";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";
const { navigationBarList, changeLogout } = useNavigationBarStore();
const { isLogin, getUserInfo, userInfo, userLogout, isValidToken, changeLoginFalse } =
  useUserStore();

const logout = () => {
  let token = sessionStorage.getItem("accessToken");
  if (userInfo !== null && token) {
    getUserInfo(token);
  }

  console.log("TheNavigationBar.vue : 로그아웃");
  // changeNavigationBarState();
  console.log(userInfo);
  if (userInfo !== null) {
    userLogout(userInfo.userId);
  } else {
    changeLogout();
    changeLoginFalse();
    console.log("네브바에서 userInfo null 검사");
  }
};
</script>

<template>
  <body style="overflow-y: scroll">
    <nav class="navbar navbar-expand-sm bg-light-subtle bg-gradient shadow-sm fixed-top">
      <div class="container">
        <router-link :to="{ name: 'home' }" class="navbar-brand" style="padding: 0; margin: 0">
          <img src="@/assets/enjoytrip_logo.png" alt="favicon" width="50" />
        </router-link>
        <router-link :to="{ name: 'home' }" class="navbar-brand" style="padding: 0; margin: 0">
          <div class="fs-3 fw-bold container">EnjoyTrip</div>
        </router-link>

        <button
          class="navbar-toggler border-0"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ms-auto mb-2 mb-lg-0 text-end">
            <li class="nav-item">
              <div>
                <router-link :to="{ name: 'board' }" class="nav-link">게시판</router-link>
              </div>
            </li>
            <template v-for="item in navigationBarList" :key="item.routeName">
              <template v-if="item.show">
                <template v-if="item.routeName === 'user-logout'">
                  <li class="nav-item">
                    <div>
                      <router-link to="/" @click.prevent="logout" class="nav-link">{{
                        item.name
                      }}</router-link>
                    </div>
                  </li>
                </template>
                <template v-else>
                  <li class="nav-item">
                    <div>
                      <router-link :to="{ name: item.routeName }" class="nav-link">{{
                        item.name
                      }}</router-link>
                    </div>
                  </li>
                </template>
              </template>
            </template>
          </ul>
        </div>
      </div>
    </nav>
  </body>
</template>

<style scoped></style>
