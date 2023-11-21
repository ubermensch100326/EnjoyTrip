<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { listSido, listGugun, listAttraction } from "@/api/map";

import TestMap from "@/components/common/TestMap.vue";
import OptionSelect from "@/components/common/OptionSelect.vue";

const props = defineProps({ selectedAttraction: Object });

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

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const attractionList = ref([]);
const attractionSelect = ref({});
const addedAttractionList = ref([]);
const keyword = ref("");
const type = ref("");

const param = ref({
    pageNo: 1,
    numOfRows: 20,
    // zscode: 0,
    sido: 0,
    gugun: 0,
    keyword: "가",
    typeNum: 12,
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
            console.log("data : " + data);
            attractionList.value = data;
            console.log("##################" + attractionList.value);
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
};

const deleteAttractionMyList = () => {
    console.log("deleteAttractionMyList 호출");
};
</script>

<template>
    <h1>TheTestView.vue</h1>
    <div class="container text-center mt-3">
        <div class="alert alert-success" role="alert">관광지 정보</div>
        <div class="row mb-2">
            <div class="col">
                <OptionSelect :optionList="sidoList" @onKeySelect="onChangeSido" />
            </div>
            <div class="col">
                <OptionSelect :optionList="gugunList" @onKeySelect="onChangeGugun" />
            </div>
            <div class="col">
                <select @change="onChangeType" v-model="type">
                    <option value="12">관광지</option>
                    <option value="14">문화시설</option>
                    <option value="15">축제공연행사</option>
                    <option value="25">여행코스</option>
                    <option value="28">레포츠</option>
                    <option value="32">숙박</option>
                    <option value="38">쇼핑</option>
                    <option value="39">음식점</option>
                </select>
            </div>
            <div class="col">
                <input type="text" v-model="keyword" @keyup.enter="onSearchButtonClick" />
                <button @click="onSearchButtonClick">검색</button>
            </div>
        </div>
        <div class="d-flex justify-content">
            <Suspense
                ><TestMap
                    :attractionList="attractionList"
                    :attractionSelect="attractionSelect"
                    @addAttractionMyList="addAttractionMyList"
                ></TestMap>
            </Suspense>

            <div class="table‑wrapper" style="width: 100%">
                <table class="table table-hover table-striped table-bordered table‑condensed">
                    <thead>
                        <tr class="text-center">
                            선택한 관광지
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            class="text-center"
                            v-for="attraction in addedAttractionList"
                            :key="attraction.attraction_id"
                        >
                            <th><img :src="attraction.first_image" alt="" width="150" /></th>
                            <th>
                                {{ attraction.title }}
                            </th>
                            <th>
                                <button @click="deleteAttractionMyList">삭제</button>
                            </th>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <table class="table table-hover">
            <thead>
                <tr class="text-center">
                    <!-- <th scope="col">관광지 상세/th></th> -->
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
        </table>
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
