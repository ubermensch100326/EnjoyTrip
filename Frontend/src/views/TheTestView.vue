<script setup>
import { ref, onMounted } from "vue";
import { listSido, listGugun, listAttraction } from "@/api/map";

import TestMap from "@/components/common/TestMap.vue";
import OptionSelect from "@/components/common/OptionSelect.vue";

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const attractionList = ref([]);
const attractionSelect = ref({});

const param = ref({
  pageNo: 1,
  numOfRows: 20,
  // zscode: 0,
  sido: 0,
  gugun: 0,
  keyword: "가",
});

onMounted(() => {
  getSidoList();
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
      console.log("##############" + sidoList.value);
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
      param.value.sido = val;
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeGugun = (val) => {
  param.value.gugun = val;
  console.log("kjSALFHDJAWKFawJKDFhAJKSDASL" + val);
  getAttractionList();
};

const getAttractionList = () => {
  listAttraction(
    param.value,
    ({ data }) => {
      console.log("data : " + data);
      attractionList.value = data;
      data.forEach((object) => {
        console.log(object);
        attractionList.value.push(object);
      });
    },
    (err) => {
      console.log(err);
    }
  );
};

const viewAttraction = (attraction) => {
  attractionSelect.value = attraction;
};
</script>

<template>
  <h1>TheTestView.vue</h1>
  <div class="container text-center mt-3">
    <div class="alert alert-success" role="alert">관광지 정보</div>
    <div class="row mb-2">
      <div class="col d-flex flex-row-reverse">
        <OptionSelect :optionList="sidoList" @onKeySelect="onChangeSido" />
      </div>
      <div class="col"><OptionSelect :optionList="gugunList" @onKeySelect="onChangeGugun" /></div>
    </div>
    <TestMap :attractionList="attractionList" :attractionSelect="attractionSelect" />
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <!-- <th scope="col">관광지 상세/th></th> -->
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

<style scoped>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}
</style>
