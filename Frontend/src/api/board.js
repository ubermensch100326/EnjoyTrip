import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listBoard(param, success, fail) {
  local.get(`/board`, { params: param }).then(success).catch(fail);
}

function viewBoard(articleno, success, fail) {
  local.get(`/board/${articleno}`).then(success).catch(fail);
}

function registerBoard(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function toModifyBoard(articleno, success, fail) {
  local.get(`/board/modify/${articleno}`).then(success).catch(fail);
}

function modifyBoard(article, success, fail) {
  local.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteBoard(articleno, success, fail) {
  local.delete(`/board/${articleno}`).then(success).catch(fail);
}

export { listBoard, viewBoard, registerBoard, toModifyBoard, modifyBoard, deleteBoard };
