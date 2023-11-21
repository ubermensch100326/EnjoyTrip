import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/TheHomeView.vue";
import TestView from "@/views/TheTestView.vue";

import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const onlyAuthUser = async (to, from, next) => {
  console.log("onlyAuthUser Execute");
  const userStore = useUserStore();
  const { userInfo, isValidToken } = storeToRefs(userStore);
  const { getUserInfo } = userStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    getUserInfo(token);
  }
  console.log("isValidToken : " + isValidToken.value);

  setTimeout(function () {
    console.log("Works!");
  }, 3000);

  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/test",
      name: "test",
      component: () => import("@/views/TheTestView.vue"),
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "register",
          name: "user-register",
          component: () => import("@/components/user/UserRegister.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserMyPage.vue"),
        },
        {
          path: "tripplan",
          name: "user-trip-plan",
          component: () => import("@/components/user/UserTripPlan.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/TheBoardView.vue"),
      redirect: { name: "board-list" },
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:boardno",
          name: "board-view",
          component: () => import("@/components/board/BoardView.vue"),
        },
        {
          path: "write",
          name: "board-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:boardno",
          name: "board-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/attraction",
      name: "attraction",
      component: TestView,
    },
  ],
});

export default router;
