import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listSido(success, fail) {
    local.get(`/map/sido`).then(success).catch(fail);
}

function listGugun(param, success, fail) {
    local.get(`/map/gugun`, { params: param }).then(success).catch(fail);
}

function listAttraction(param, success, fail) {
    local.get(`/map/attraction`, { params: param }).then(success).catch(fail);
}

function createMyPlan(param, success, fail) {
    local.post(`/map/plan`, JSON.stringify(param)).then(success).catch(fail);
}

function deleteMyPlan(param, success, fail) {
    local.delete(`/map/plan`, { params: param }).then(success).catch(fail);
}

function getMyPlan(param, success, fail) {
    local.get(`/map/plan`, { params: param }).then(success).catch(fail);
}

export { listSido, listGugun, listAttraction, createMyPlan, getMyPlan, deleteMyPlan };
