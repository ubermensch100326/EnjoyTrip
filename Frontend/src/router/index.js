import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/TheHomeView.vue";
import TestView from "@/views/TheTestView.vue";

import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const onlyAuthUser = async (to, from, next) => {
    console.log("onlyAuthUser Execute");
    const { isLogin, getUserInfo, userInfo, isValidToken, changeLoginFalse, changeLoginTrue } =
        useUserStore();

    let token = sessionStorage.getItem("accessToken");
    if (userInfo != null && token) {
        getUserInfo(token);
    }
    console.log("isValidToken : " + isValidToken);

    // setTimeout(function () {
    //   console.log("!!!isLogin : " + isLogin);
    //   console.log("!!!!userInfo : " + userInfo.userId);
    //   console.log("Works!");
    // }, 3000);

    console.log("!!!isLogin : " + isLogin);
    console.log("!!!!userInfo : " + userInfo);

    if (!isValidToken || userInfo == null) {
        console.log(11111111);
        // isLogin.value = false;
        // isValidToken.value = false;
        // isLogin.value = false;
        next({ name: "user-login" });
    } else {
        console.log(22222222);
        next();
    }
};

const check = async (to, from, next) => {
    const { isLogin, tempUserInfo, userInfo, isValidToken, changeLoginFalse, changeLoginTrue } =
        useUserStore();

    let token = sessionStorage.getItem("accessToken");
    if (userInfo != null && token) {
        tempUserInfo(token);
    }
    console.log("isValidToken : " + isValidToken);

    next();
};

const alreadyLogin = async (to, from, next) => {
    const { isValidToken, userInfo, isLogin } = useUserStore();
    if (isValidToken && userInfo && isLogin) {
        alert("이미 로그인된 상태입니다!");
        next({ name: "home" });
    } else {
        next();
    }
};

// 나중에 네브바 인증처리도 해야함

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            beforeEnter: check,
            component: HomeView,
        },
        {
            path: "/test",
            name: "test",
            beforeEnter: check,
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
                    beforeEnter: alreadyLogin,
                    component: () => import("@/components/user/UserLogin.vue"),
                },
                {
                    path: "register",
                    name: "user-register",
                    beforeEnter: alreadyLogin,
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
                    beforeEnter: check,
                    component: () => import("@/components/user/UserTripPlan.vue"),
                    redirect: { name: "board-my-list" },
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
                    beforeEnter: check,
                    component: () => import("@/components/board/BoardList.vue"),
                },
                {
                    path: "myList",
                    name: "board-my-list",
                    beforeEnter: check,
                    component: () => import("@/components/board/BoardMyList.vue"),
                },
                {
                    path: "view/:boardno",
                    name: "board-view",
                    beforeEnter: check,
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
            beforeEnter: check,
            component: TestView,
        },
    ],
});

export default router;
