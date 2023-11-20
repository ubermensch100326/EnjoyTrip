<script setup>
import { ref, onMounted } from "vue";
import { listStations } from "@/api/estation";
import { listSido, listGugun } from "@/api/map";
import { listAttraction } from "@/api/attraction";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";

// const serviceKey = import.meta.env.VITE_OPEN_API_SERVICE_KEY;
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const chargingStations = ref([]);
const selectStation = ref({});

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});

onMounted(() => {
  getChargingStations();
  // getSidoList();
});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sidoName, value: sido.sidoCode });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  listGugun(
    { sido: val },
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugunName, value: gugun.gugunCode });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  param.value.zscode = val;
  getChargingStations();
  // test();
};

const getChargingStations = () => {
  listStations(
    param.value,
    ({ data }) => {
      chargingStations.value = data.items[0].item;
    },
    (err) => {
      console.log(err);
    }
  );
};

const attractionList = ref([]);
const attractionSelect = ref({});

const test = () => {
  listAttraction(
    param.value,
    ({ data }) => {
      console.log(data);
      attractionList.value = data.items[0].item;
    },
    (err) => {
      console.log(err);
    }
  );
};

const viewAttraction = (attraction) => {
  attractionSelect.value = attraction;
};

const viewStation = (station) => {
  selectStation.value = station;
};
</script>

<template>
  <div class="container text-center mt-3">
    <div class="alert alert-success" role="alert">전기차 충전소</div>
    <div class="row mb-2">
      <div class="col d-flex flex-row-reverse">
        <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
      </div>
      <div class="col"><VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" /></div>
    </div>
    <VKakaoMap :stations="chargingStations" :selectStation="selectStation" />
    <!-- <VKakaoMap :attractionList="attractionList" :attractionSelect="attractionSelect" /> -->
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">충전소명</th>
          <th scope="col">충전소ID</th>
          <th scope="col">충전기상태</th>
          <th scope="col">위치</th>
          <th scope="col">위도</th>
          <th scope="col">경도</th>
        </tr>
      </thead>
      <tbody>
        <!-- <tr
          class="text-center"
          v-for="attraction in attractionList"
          :key="attraction.statId + attraction.chgerId"
          @click="viewAttraction(attraction)"
        >
          <th>{{ attraction.statId }}</th>
        </tr> -->
      </tbody>
    </table>
  </div>
</template>

<style>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}
</style>
