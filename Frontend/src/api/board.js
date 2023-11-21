import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listBoard(param, success, fail) {
  local.get(`/board`, { params: param }).then(success).catch(fail);
}

function viewBoard(boardno, success, fail) {
  local.get(`/board/${boardno}`).then(success).catch(fail);
}

function listComment(param, success, fail) {
  console.log("댓글 요청!!!!!!!!!!!!!!!!!!!!!");
  local.get(`/comment`, { params: param }).then(success).catch(fail);
}

function registerBoard(board, success, fail) {
  console.log("boardjs board", board);
  local.post(`/board`, JSON.stringify(board)).then(success).catch(fail);
}

function toModifyBoard(boardno, success, fail) {
  local.get(`/board/modify/${boardno}`).then(success).catch(fail);
}

function modifyBoard(board, success, fail) {
  local.put(`/board`, JSON.stringify(board)).then(success).catch(fail);
}

function deleteBoard(boardno, success, fail) {
  local.delete(`/board/${boardno}`).then(success).catch(fail);
}

export {
  listBoard,
  viewBoard,
  registerBoard,
  toModifyBoard,
  modifyBoard,
  deleteBoard,
  listComment,
};
