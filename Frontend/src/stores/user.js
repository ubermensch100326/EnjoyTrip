import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";
import { useNavigationBarStore } from "./navigation-bar";

export const useUserStore = defineStore(
  "userStore",
  () => {
    const router = useRouter();

    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);

    const userLogin = async (loginUser) => {
      await userConfirm(
        loginUser,
        (response) => {
          // api/user.js에서 useConfirm axios 요청을 하고 success를 반환받으면 reponse.status가 201임
          // http-status에 CREATE는 201이라고 해놨음
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response;
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];

            // 로그인에 성공했으면 isLogin.value 가 true로 설정
            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;

            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
            console.log("sessionStorage 에 Token 담음. : ", isLogin.value);
          } else {
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error("api/user.js : " + error);
        }
      );
    };

    const getUserInfo = (token) => {
      // 가져온 token을 디코딩함
      let decodeToken = jwtDecode(token);
      findById(
        // userId를 가지고 findById axios 호출 (밑에는 잠시 대기)
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            // UserInfo에 데이터 저장해두기
            userInfo.value = response.data.userInfo;
          } else {
            // 정상적으로 권한이 없다고 하면 여기로 들어옴
            console.log("찾는 유저 정보 없음!!!!  : " + decodeToken.userId);
          }
        },
        // 토큰 자체는 진짜인데 더 이상 유효하지 않은 정보를 가지고 있어서 exception이 나는 경우는 spring에서 custom error를 던짐
        async (error) => {
          console.error(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          // 더이상 사용할 수 없는 토큰이므로 false로 바꾸고 토큰 재생성하러 감
          isValidToken.value = false;

          await tokenRegenerate();
        }
      );
    };

    const tempUserInfo = (token) => {
      // 가져온 token을 디코딩함
      let decodeToken = jwtDecode(token);
      findById(
        // userId를 가지고 findById axios 호출 (밑에는 잠시 대기)
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            // UserInfo에 데이터 저장해두기
            userInfo.value = response.data.userInfo;
          } else {
            // 정상적으로 권한이 없다고 하면 여기로 들어옴
            console.log("찾는 유저 정보 없음!!!!  : " + decodeToken.userId);
          }
        },
        // 토큰 자체는 진짜인데 더 이상 유효하지 않은 정보를 가지고 있어서 exception이 나는 경우는 spring에서 custom error를 던짐
        async (error) => {
          console.error(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          isValidToken.value = false;

          await tempRegenerate();
        }
      );
    };

    const tokenRegenerate = async () => {
      // 사용자 정보를 들고 axios 요청
      // tokenRegeneration에서는 세션에 있는 refreshToken을 가지고 요청
      await tokenRegeneration(
        JSON.stringify(userInfo.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                console.log("------------------------");
                console.log(isLogin.value);
                console.log(isValidToken.value);
                // alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                // sessionStorage.removeItem("accessToken");
                // sessionStorage.removeItem("refreshToken");
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
                console.log("로그인 페이지로 이동하기 직전입니다!!!!");
                console.log(isLogin.value);
                console.log(isValidToken.value);
                console.log("-----------------------------");
                router.push({ name: "user-login" });
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = null;
              }
            );
          }
        }
      );
    };

    const tempRegenerate = async () => {
      // 사용자 정보를 들고 axios 요청
      // tokenRegeneration에서는 세션에 있는 refreshToken을 가지고 요청
      await tokenRegeneration(
        JSON.stringify(userInfo.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
              }
            );
          }
        }
      );
    };

    const userLogout = async (userid) => {
      await logout(
        userid,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            userInfo.value = null;
            isValidToken.value = false;
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
          } else {
            console.error("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const { changeLogin, changeLogout } = useNavigationBarStore();
    watch(isLogin, () => {
      if (isLogin.value) {
        console.log("네브바를 로그인 상태로 변경합니다!");
        changeLogin();
      } else {
        console.log("네브바를 로그아웃 상태로 변경합니다!");
        changeLogout();
      }
    });

    const changeLoginFalse = () => {
      isLogin.value = false;
      isValidToken.value = false;
    };

    const changeLoginTrue = () => {
      isLogin.value = true;
      isValidToken.value = true;
    };

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
      changeLoginFalse,
      changeLoginTrue,
      tempUserInfo,
      tempRegenerate,
    };
  },
  { persist: true }
);
