<script setup>
import { ref, watch, onMounted } from "vue";

var map;
var positions = [];
const markers = ref([]);
const overlays = ref([]);

const props = defineProps({ attractionList: Array, attractionSelect: Object });

watch(
  () => props.attractionList.value,
  () => {
    deleteMarkers();
    positions = [];
    props.attractionList.forEach((attraction) => {
      let obj = {};
      obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
      obj.title = attraction.title;
      obj.addr = attraction.addr1;
      obj.first_image = attraction.first_image;
      obj.tel = attraction.tel;
      positions.push(obj);
    });

    for (let i = 0; i < positions.length; i++) {
      var data = positions[i];
      loadMarkers(data);
    }
  },
  { deep: true }
);

watch(
  () => props.attractionSelect.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(
      props.attractionSelect.latitude,
      props.attractionSelect.longitude
    );
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  console.log(container);
  var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };

  map = new kakao.maps.Map(container, options);
  console.log(map);
  // loadMarkers();
};

// function closeOverlay() {
//   console.log("closeOverLay 호출");
//   overlay.setMap(null);
// }

const loadMarkers = (position) => {
  var marker = new kakao.maps.Marker({
    map: map, // 마커를 표시할 지도
    position: position.latlng, // 마커를 표시할 위치
    // title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
    clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
    // image: markerImage, // 마커의 이미지
  });

  // 마커 배열에 각 마커 추가
  markers.value.push(marker);

  ///////////////////////////////////////////////////
  var content = document.createElement("div");
  content.classList.add("wrap");
  content.style.backgroundColor = "white";

  var infoDiv = document.createElement("div");
  infoDiv.classList.add("info");

  var titleDiv = document.createElement("div");
  titleDiv.classList.add("title");
  titleDiv.style.backgroundColor = "gray";
  titleDiv.textContent = position.title;

  var closeButton = document.createElement("button");
  closeButton.classList.add("close");
  closeButton.setAttribute("title", "닫기");
  //   closeButton.textContent = "x";
  closeButton.onclick = closeBtn; // closeOverlay 함수를 연결합니다.

  titleDiv.appendChild(document.createTextNode(" "));
  titleDiv.appendChild(closeButton);

  var bodyDiv = document.createElement("div");
  bodyDiv.classList.add("body");

  var imgDiv = document.createElement("div");
  imgDiv.classList.add("img");

  var image = document.createElement("img");
  image.setAttribute("src", "/src/assets/noimage.png");
  image.setAttribute("width", "73");
  image.setAttribute("height", "70");
  if (position.first_image !== "") image.setAttribute("src", position.first_image);
  //position.first_image

  imgDiv.appendChild(image);

  var descDiv = document.createElement("div");
  descDiv.classList.add("desc");

  var ellipsisDiv = document.createElement("div");
  ellipsisDiv.classList.add("ellipsis");
  ellipsisDiv.textContent = position.addr;

  var linkDiv = document.createElement("div");
  var paragraph = document.createElement("p");
  paragraph.textContent = position.tel;
  linkDiv.appendChild(paragraph);

  descDiv.appendChild(ellipsisDiv);
  descDiv.appendChild(linkDiv);

  bodyDiv.appendChild(imgDiv);
  bodyDiv.appendChild(descDiv);

  infoDiv.appendChild(titleDiv);
  infoDiv.appendChild(bodyDiv);

  content.appendChild(infoDiv);
  ////////////////////////////////////////

  var overlay = new kakao.maps.CustomOverlay({
    content: content,
    clickable: true,
    xAnchor: 0.5,
    yAnchor: 1.3,
    zIndex: 3,
    position: marker.getPosition(),
  });

  var closeBtn = document.createElement("button");
  closeBtn.innerHTML = "닫기";
  closeBtn.onclick = function () {
    overlay.setMap(null);
  };

  content.appendChild(closeBtn);
  overlay.setContent(content);

  kakao.maps.event.addListener(marker, "click", function () {
    overlay.setMap(map);
  });
  // overlays.push(overlay);

  // 마커 배열의 마커들에게 클릭 이벤트 추가 하는 부분.
  markers.value.forEach((marker) => {
    kakao.maps.event.addListener(marker, "click", function () {
      overlays.value.setMap(map);
    });
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  overlays.value.push(overlay);

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
  if (overlays.value.length > 0) {
    overlays.value.forEach((overlay) => overlay.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 700px;
}
</style>
