import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

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
      component: () => import("../views/TestView.vue"),
    },
    {
      path: "/user",
      name: "user",
      component: () => import("../views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("../components/user/UserLogin.vue"),
        },
      ],
    },
  ],
});

export default router;
