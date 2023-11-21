<script setup>
import { ref, watch, onMounted } from "vue";

var map;
var overlay = {};
var overlays = [];
const positions = ref([]);
const markers = ref([]);

const props = defineProps({ attractionList: Array, attractionSelect: Object });

watch(
    () => props.attractionList.value,
    () => {
        positions.value = [];
        props.attractionList.forEach((attraction) => {
            let obj = {};
            obj.latlng = new kakao.maps.LatLng(attraction.latitude, attraction.longitude);
            obj.title = attraction.statNm;

            positions.value.push(obj);
        });
        loadMarkers();
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

function closeOverlay() {
    console.log("closeOverLay 호출");
    overlay.setMap(null);
}

const loadMarkers = () => {
    // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
    deleteMarkers();

    // 마커를 생성합니다
    markers.value = [];
    console.log("position is => " + positions.value);
    positions.value.forEach((position) => {
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            // title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
            clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
            // image: markerImage, // 마커의 이미지
        });

        // 마커 배열에 각 마커 추가
        markers.value.push(marker);

        var content =
            '<div class="wrap" style="background-color: white;">' +
            '    <div class="info">' +
            '        <div class="title" style="background-color: gray;">' +
            position.title +
            "             " +
            '            <button class="close" onclick="closeOverlay()" title="닫기">x</button>' +
            "        </div>" +
            '        <div class="body">' +
            '            <div class="img">' +
            '                <img src="' +
            position.first_image +
            '" width="73" height="70">' +
            "           </div>" +
            '            <div class="desc">' +
            '                <div class="ellipsis">' +
            position.addr1 +
            "</div>" +
            '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
            '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

        overlay = new kakao.maps.CustomOverlay({
            content: content,
            map: map,
            clickable: true,
            xAnchor: 0.5,
            yAnchor: 1.3,
            zIndex: 3,
            position: marker.getPosition(),
        });

        overlay.setContent(content);
        // overlays.push(overlay);
    });

    // 마커 배열의 마커들에게 클릭 이벤트 추가 하는 부분.
    markers.value.forEach((marker) => {
        kakao.maps.event.addListener(marker, "click", function () {
            overlays.setMap(map);
        });
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
    <div id="map"></div>
</template>

<style scoped>
#map {
    width: 100%;
    height: 700px;
}
</style>
