import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listBoard(param, success, fail) {
    local.get(`/board`, { params: param }).then(success).catch(fail);
}

function listMyBoard(param, success, fail) {
    local.get(`/board/mylist`, { params: param }).then(success).catch(fail);
}

function viewBoard(boardno, success, fail) {
    local.get(`/board/${boardno}`).then(success).catch(fail);
}

function listComment(param, success, fail) {
    console.log("댓글 요청!!!!!!!!!!!!!!!!!!!!!");
    local.get(`/board/comment`, { params: param }).then(success).catch(fail);
}

function modifyBoard(board, success, fail) {
    local.put(`/board`, JSON.stringify(board)).then(success).catch(fail);
}

function registerBoard(board, success, fail) {
    console.log("boardjs board", board);
    local.post(`/board`, JSON.stringify(board)).then(success).catch(fail);
}

function registerComment(comment, success, fail) {
    local.post(`/board/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function toModifyBoard(boardno, success, fail) {
    local.get(`/board/modify/${boardno}`).then(success).catch(fail);
}

function modifyComment(editParam, success, fail) {
    local.put(`/board/comment`, JSON.stringify(editParam)).then(success).catch(fail);
}

function deleteBoard(boardno, success, fail) {
    local.delete(`/board/${boardno}`).then(success).catch(fail);
}

function deleteComment(param, success, fail) {
    local.delete(`/board/comment`, { params: param }).then(success).catch(fail);
}

function userBoardList(userInfo, success, fail) {
    console.log(userInfo);
    local.get(`/board/list`, { params: userInfo }).then(success).catch(fail);
}
export {
    listBoard,
    listMyBoard,
    viewBoard,
    registerBoard,
    toModifyBoard,
    modifyBoard,
    deleteBoard,
    listComment,
    deleteComment,
    modifyComment,
    registerComment,
    userBoardList,
};
