import { ref } from "vue";
import { defineStore } from "pinia";

export const useNavigationBarStore = defineStore(
  "navigation",
  () => {
    const navigationBarList = ref([
      { name: "회원가입", show: true, routeName: "user-register" },
      { name: "로그인", show: true, routeName: "user-login" },
      { name: "마이페이지", show: false, routeName: "user-mypage" },
      { name: "여행계획", show: false, routeName: "board-my-list" },
      { name: "로그아웃", show: false, routeName: "user-logout" },
    ]);

    const changeNavigationBarState = () => {
      navigationBarList.value = navigationBarList.value.map((item) => ({
        ...item,
        show: !item.show,
      }));
    };

    const changeLogin = () => {
      navigationBarList.value.forEach((item) => {
        if (item.name === "마이페이지" || item.name === "여행계획" || item.name === "로그아웃") {
          item.show = true;
        } else {
          item.show = false;
        }
      });
    };

    const changeLogout = () => {
      navigationBarList.value.forEach((item) => {
        if (item.name === "회원가입" || item.name === "로그인") {
          item.show = true;
        } else {
          item.show = false;
        }
      });
    };

    return {
      navigationBarList,
      // changeNavigationBarState,
      changeLogin,
      changeLogout,
    };
  },
  { persist: true }
);
