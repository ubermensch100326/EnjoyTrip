<script setup>
import { ref, watch, onMounted } from "vue";

var map;
const linePath = ref([]);
const positions = ref([]);
const markers = ref([]);
var dots = [];

const props = defineProps({
  modifiable: Boolean,
  searchAttraction: Array,
  selectedAttraction: Object,
});

// // 선택한 관광지를 맵 중앙으로 맞추는 watch
// watch(
//     () => props.selectedAttraction.value,
//     () => {
//         // 이동할 위도 경도 위치를 생성합니다
//         var moveLatLon = new kakao.maps.LatLng(
//             props.selectedAttraction.lat,
//             props.selectedAttraction.lng
//         );

//         // 지도 중심을 부드럽게 이동시킵니다
//         // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//         map.panTo(moveLatLon);
//     },
//     { deep: true }
// );

// // 관광지를 검색할 때 변경될 때 마다 관광지 마커를 찍어주는 watch
// watch(
//   () => props.searchAttraction.value,
//   () => {
//     positions.value = [];
//     props.searchAttraction.forEach((attraction) => {
//       let obj = {};
//       obj.latlng = new kakao.maps.LatLng(attraction.lat, attraction.lng);
//       obj.title = station.statNm;

//       positions.value.push(obj);
//     });
//     loadMarkers();
//   },
//   { deep: true }
// );

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };

  if (props.modifiable) {
    console.log(props.modifiable);
  } else {
    console.log(props.modifiable);

    linePath.value = [
      new kakao.maps.LatLng(33.452344169439975, 126.56878163224233),
      new kakao.maps.LatLng(33.452739313807456, 126.5709308145358),
      new kakao.maps.LatLng(33.45178067090639, 126.5726886938753),
      new kakao.maps.LatLng(33.45133510810506, 126.57159381623066),
      new kakao.maps.LatLng(33.44955812811862, 126.5713551811832),
      new kakao.maps.LatLng(33.449986291544086, 126.57263296172184),
      new kakao.maps.LatLng(33.450682513554554, 126.57321034054742),
      new kakao.maps.LatLng(33.451346760004206, 126.57235740081413),
    ];
  }

  map = new kakao.maps.Map(container, options);

  // loadMarkers();
  loadMyMarkers();
};

const loadMyMarkers = () => {
  deleteMarkers();
  var first = false;

  var myLine = new kakao.maps.Polyline({
    map: map, // 선을 표시할 지도입니다
    path: linePath.value, // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
    strokeWeight: 3, // 선의 두께입니다
    strokeColor: "#db4040", // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
    strokeStyle: "solid", // 선의 스타일입니다
    // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
  });

  markers.value = [];

  linePath.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  if (first) {
    console.log(first);
  } else {
    console.log(first);

    // linePath.value.forEach((element) => {

    // });

    // displayCircleDot(linePath.value, myLine.getLength());
  }

  // var myLine = new kakao.maps.Polyline({
  //     path: linePath.value, // 선을 구성하는 좌표배열 입니다
  //     strokeWeight: 5, // 선의 두께 입니다
  //     strokeColor: "#FFAE00", // 선의 색깔입니다
  //     strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
  //     strokeStyle: "solid", // 선의 스타일입니다
  // });

  // 지도에 선을 표시합니다
  myLine.setMap(map);
};

// function displayCircleDot(position, distance) {
//     // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
//     var circleOverlay = new kakao.maps.CustomOverlay({
//         content: '<span class="dot"></span>',
//         position: position,
//         zIndex: 1,
//     });

//     // 지도에 표시합니다
//     circleOverlay.setMap(map);

//     if (distance > 0) {
//         // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
//         var distanceOverlay = new kakao.maps.CustomOverlay({
//             content:
//                 '<div class="dotOverlay">거리 <span class="number">' + distance + "</span>m</div>",
//             position: position,
//             yAnchor: 1,
//             zIndex: 2,
//         });

//         // 지도에 표시합니다
//         distanceOverlay.setMap(map);
//     }

//     // // 배열에 추가합니다
//     // dots.push({ circle: circleOverlay, distance: distanceOverlay });
// }

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //const imgSize = new kakao.maps.Size(24, 35);
  //const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];

  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );
  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <!-- <h1>KakaoMap.vue</h1> -->
  <div id="map"></div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 700px;
}
</style>
