import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listSido(success, fail) {
  local.get(`/map/sido`).then(success).catch(fail);
}

function listGugun(param, success, fail) {
  local.get(`/map/gugun`, { params: param }).then(success).catch(fail);
}

function listAttraction(param, success, fail) {
  console.log("start");
  local.get(`/attraction`, { params: param }).then(success).catch(fail);
  console.log("end");
}

export { listSido, listGugun, listAttraction };
