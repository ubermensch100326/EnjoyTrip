import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useNavigationStore = defineStore("navigation", () => {
  const navList = ref([
    { name: "회원가입", show: true, routeName: "regist" },
    { name: "로그인", show: true, routeName: "login" },
    { name: "내정보", show: false, routeName: "mypage" },
    { name: "로그아웃", show: false, routeName: "logout" },
  ]);

  const changeNavState = () => {
    navList.value = navList.value.map((item) => ({ ...item, show: !item.show }));
  };

  return {
    navList,
    changeNavState,
  };
});
