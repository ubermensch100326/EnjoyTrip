<script setup>
import { ref, onMounted, watch } from "vue";
import {
  listSido,
  listGugun,
  listAttraction,
  createMyPlan,
  getMyPlan,
  deleteMyPlan,
} from "@/api/map";

import TestMap from "@/components/common/TestMap.vue";
import OptionSelect from "@/components/common/OptionSelect.vue";

const props = defineProps({
  selectedAttraction: Object,
  boardno: Number,
  board: Object,
});

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const attractionList = ref([]);
const attractionSelect = ref({});
const addedAttractionList = ref([]);
const keyword = ref("");
const type = ref("0");

const planParam = ref({
  board_no: props.boardno,
});

const param = ref({
  pageNo: 1,
  numOfRows: 30,
  sido: 0,
  gugun: 0,
  keyword: "가",
  typeNum: 12,
});

watch(
  () => props.selectedAttraction.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(
      props.selectedAttraction.lat,
      props.selectedAttraction.lng
    );

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  getSidoList();
  getPlan();
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
  console.log("On change Gugun : val => " + val);
};

const onChangeType = () => {
  param.value.typeNum = type.value;
  console.log("On change type : val => " + type.value);
};

// const onChangeType

const onSearchButtonClick = () => {
  console.log(11111);
  param.value.keyword = keyword.value;
  getAttractionList();
};

const getAttractionList = () => {
  listAttraction(
    param.value,
    ({ data }) => {
      attractionList.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const viewAttraction = (attraction) => {
  console.log("@@@@@@@@@@@@@@  viewAttraction change => " + attraction.attraction_id);
  attractionSelect.value = attraction;
};

const addAttractionMyList = (attraction) => {
  addedAttractionList.value.push(attraction);
  console.log(
    " @@@@@@@@@@@@@@@@@@@@@@@@ addAttractionMyList @@@@@@@@@@@@@@@@@@@@@@@@" +
      JSON.stringify(addedAttractionList.value)
  );
};

const deleteAttractionMyList = (index) => {
  addedAttractionList.value.splice(index, 1);
};

/** 현재 추가된 경로 배열이 필요합니다. */
const savePlan = () => {
  console.log("Call Save Plan!!!!!!!!!!!!!!!!!!!!!!");
  deleteMyPlan(
    planParam.value,
    ({ data }) => {
      var index = 1;

      addedAttractionList.value.forEach((plan) => {
        // 저장할 관광지의 여행 계획 순서 추가.
        plan.order = index;
        plan.board_no = planParam.value.board_no;
        index++;
        createMyPlan(
          plan,
          ({ data }) => {
            console.log(data);
          },
          (err) => {
            console.log(err);
          }
        );
      });
    },
    (err) => {
      console.log(err);
    }
  );
};

/** 계획을 불러올 게시물의 board_no 가 필요합니다. */
const getPlan = () => {
  getMyPlan(
    planParam.value,
    ({ data }) => {
      addedAttractionList.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};
</script>

<template>
  <!-- <h1>TheTestView.vue</h1> -->
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="fs-3 fw-bold container">{{ board.title }}</div>
      <div style="height: 30px"></div>
      <div class="col-lg-10">
        <div class="row mb-2">
          <!-- <div class="col alert alert-success" role="alert">관광지정보</div>
          <div class="col-md-2">
            <OptionSelect :optionList="sidoList" @onKeySelect="onChangeSido" />
          </div>
          <div class="col-md-2">
            <OptionSelect :optionList="gugunList" @onKeySelect="onChangeGugun" />
          </div>
          <div class="col-md-2">
            <select class="form-select form-select-bg w-50" @change="onChangeType" v-model="type">
              <option value="0" selected disabled>관광지유형</option>
              <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">축제공연행사</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
            </select>
          </div> -->
          <!-- <div class="col">
            <input type="text" v-model="keyword" @keyup.enter="onSearchButtonClick" />
            <button class="pb-2" @click="onSearchButtonClick">검색</button>
            <button @click="savePlan">계획 저장</button>
              <button @click="getPlan">계획 불러오기</button>
          </div> -->
        </div>
        <div class="d-flex justify-content border-1">
          <Suspense>
            <TestMap
              :attractionList="attractionList"
              :attractionSelect="attractionSelect"
              :addedAttractionList="addedAttractionList"
              @addAttractionMyList="addAttractionMyList"
            ></TestMap>
          </Suspense>

          <div class="table table-borderless" style="width: 100%">
            <div class="fs-5 fw-bold container mb-3">다녀온 여행지</div>
            <table class="table table-borderless table‑condensed table-hover">
              <thead>
                <tr class="text-center">
                  <th>사진</th>
                  <th>이름</th>
                  <!-- <th>비고</th> -->
                </tr>
              </thead>
              <tbody>
                <tr
                  class="text-center"
                  v-for="(attraction, index) in addedAttractionList"
                  :key="attraction.attraction_id"
                >
                  <th class="rounded-0">
                    <img
                      v-if="attraction.first_image"
                      :src="attraction.first_image"
                      alt=""
                      @error="handleImageError"
                      width="150"
                      class="rounded-3"
                    />
                    <img
                      v-else
                      src="../assets/noimage.png"
                      alt="noimage"
                      @error="handleImageError"
                      width="150"
                      class="rounded-3"
                    />
                  </th>
                  <th class="fs-6 align-middle fw-normal rounded-0">
                    {{ attraction.title }}
                  </th>
                  <!-- <th>
                    <button @click="deleteAttractionMyList(index)">삭제</button>
                  </th> -->
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <!-- <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">관광지 상세/th></th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="text-center"
              v-for="attraction in attractionList"
              :key="attraction.attraction_id"
              @click="viewAttraction(attraction)"
            >
              <th><img :src="attraction.first_image" width="50" /></th>
              <th>{{ attraction.attraction_id }}</th>
              <th>{{ attraction.title }}</th>
            </tr>
          </tbody>
        </table> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}

.table‑wrapper {
  max‑width: 600px;
  overflow: scroll;
}
</style>
