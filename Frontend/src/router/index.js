import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/TheHomeView.vue";

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
          component: () => import("@/components/user/UserMyPage.vue"),
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
          path: "view/:articleno",
          name: "board-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "board-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
