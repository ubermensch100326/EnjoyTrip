import { ref } from "vue";
import { defineStore } from "pinia";

export const useNavigationBarStore = defineStore("navigation", () => {
  const navigationBarList = ref([
    { name: "회원가입", show: true, routeName: "user-register" },
    { name: "로그인", show: true, routeName: "user-login" },
    { name: "마이페이지", show: false, routeName: "user-mypage" },
    { name: "내 여행계획", show: false, routeName: "user-trip-plan" },
    { name: "로그아웃", show: false, routeName: "user-logout" },
  ]);

  const changeNavigationBarState = () => {
    navigationBarList.value = navigationBarList.value.map((item) => ({
      ...item,
      show: !item.show,
    }));
  };

  return {
    navigationBarList,
    changeNavigationBarState,
  };
});
