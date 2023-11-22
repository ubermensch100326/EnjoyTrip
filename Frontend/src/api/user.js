import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  console.log("api/user.js : userConfirm => ", param);
  await local.post(`/user/login`, param).then(success).catch(fail);
  console.log("api/user.js : userConfirm =>  userConfirm ok");
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

// 데이터 요청할 때 access toekn을 header에 담아서 spring으로 감
async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

function modifyUserInfo(userInfo, success, fail) {
  local.put(`/user/info`, JSON.stringify(userInfo)).then(success).catch(fail);
}

function registerUser(userData, success, fail) {
  local.post(`/user/info`, JSON.stringify(userData)).then(success).catch(fail);
}

export { userConfirm, logout, findById, tokenRegeneration, modifyUserInfo, registerUser };
