<template>
  <div>
    <div id="map"></div>
    <div class="buttons">
      <button class="map-btn" @click="initMap">내 위치</button>
      <button class="btn" @click="goCreateThunder">번개 만들기</button>
    </div>
    <List />
  </div>
</template>

<script setup>
import List from '@/components/home/List.vue'

import { onMounted, ref, toRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useThunderStore } from '@/stores/thunder';

const store = useThunderStore();

let map = null;
const markers = ref([]);
const overlays = ref([]);

const router = useRouter();
const goCreateThunder = () => {
  router.push({ name: 'thunderCreate' });
};

const getAddressName = (address) => {
  if (address) {
    return address.split(')')[0] + ')';
  }
  return '';
}

// 이미지 URL 가져오는 함수
const getImageUrl = (imagePath) => {
  return imagePath ? new URL(`/src/assets/thunder/${imagePath}`, import.meta.url).href :
    new URL(`@/assets/thunder/thunderDefault.png`, import.meta.url).href;
};

const initMap = function () {
  let myCenter = new kakao.maps.LatLng(33.450701, 126.570667); // 카카오본사
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      const lat = position.coords.latitude;
      const lon = position.coords.longitude;
      myCenter = new kakao.maps.LatLng(lat, lon);
      map.setCenter(myCenter);
    });
  }

  const container = document.getElementById('map');
  const options = {
    center: myCenter,
    level: 6,
  }; // 지도 객체를 등록합니다.
  map = new kakao.maps.Map(container, options);

  // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
  const mapTypeControl = new kakao.maps.MapTypeControl();
  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
  // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 생성합니다
  const zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

  // 기존 마커 및 오버레이 추가
  addMarkers(store.thunderList);
};

const addMarkers = (thunders) => {
  if (!window.kakao || !window.kakao.maps) {
    console.error("Kakao Maps API is not loaded yet.");
    return;
  }

  // 기존 마커 및 오버레이 제거
  markers.value.forEach(marker => marker.setMap(null));
  overlays.value.forEach(overlay => overlay.setMap(null));
  markers.value = [];
  overlays.value = [];

  thunders.forEach(thunder => {
    const coords = new kakao.maps.LatLng(thunder.latitude, thunder.longitude);
    const marker = new kakao.maps.Marker({
      map: toRaw(map),
      position: coords,
      title: thunder.title // 마커에 제목 추가
    });

    // 마커 이벤트 리스너 추가
    kakao.maps.event.addListener(marker, 'mouseover', () => {
      marker.setZIndex(100);
      marker.setOpacity(0.8);
    });

    kakao.maps.event.addListener(marker, 'mouseout', () => {
      marker.setZIndex(0);
      marker.setOpacity(1);
    });

    // 오버레이 콘텐츠 생성
    const customoverlay = document.createElement('div');
    customoverlay.className = "customoverlay";

    const content = document.createElement('div');
    content.className = "customoverlay-content";

    const closebutton = document.createElement('button');
    closebutton.className = "close";
    closebutton.appendChild(document.createTextNode('X'));

    const info = document.createElement('div');
    info.className = "thunder-info";

    const image = document.createElement('img');
    image.className = "thunder-image";
    image.src = getImageUrl(thunder.image);

    const detail = document.createElement('div');
    detail.className = "thunder-details";

    const category = document.createElement('div');
    category.className = "category";
    category.appendChild(document.createTextNode(thunder.category));

    const title = document.createElement('h3');
    title.appendChild(document.createTextNode(thunder.title));

    const datelocation = document.createElement('div');
    datelocation.className = "date-location";

    const dateTime = document.createElement('p');
    dateTime.appendChild(document.createTextNode(thunder.dateTime));

    const location = document.createElement('p');
    location.appendChild(document.createTextNode(getAddressName(thunder.addressName)));

    const detailbutton = document.createElement('button');
    detailbutton.className = "details";
    detailbutton.appendChild(document.createTextNode('자세히 보기'));

    datelocation.appendChild(dateTime);
    datelocation.appendChild(location);

    detail.appendChild(category);
    detail.appendChild(title);
    detail.appendChild(datelocation);
    
    info.appendChild(image);
    info.appendChild(detail);
    
    content.appendChild(closebutton);
    content.appendChild(info);
    content.appendChild(detailbutton);

    customoverlay.appendChild(content);

    // 인라인 스타일 적용
    customoverlay.style.position = 'absolute';
    customoverlay.style.bottom = '30px';
    customoverlay.style.borderRadius = '20px';
    customoverlay.style.border = '1px solid #ccc';
    customoverlay.style.borderBottom = '2px solid #ddd';
    customoverlay.style.fontSize = '12px';
    customoverlay.style.padding = '10px';
    customoverlay.style.background = '#fff';
    customoverlay.style.boxShadow = '0 2px 5px rgba(0, 0, 0, 0.15)';

    const overlay = new kakao.maps.CustomOverlay({
      content: customoverlay,
      map: null, // 처음엔 닫힌 상태
      position: coords
    });

    closebutton.onclick = () => closeOverlay(overlay);
    detailbutton.onclick = () => viewDetails(thunder.id);

    kakao.maps.event.addListener(marker, 'click', () => {
      closeAllOverlays(); // 모든 오버레이를 닫기
      overlay.setMap(toRaw(map));
    });

    markers.value.push(marker);
    overlays.value.push(overlay);
  });
};

const closeAllOverlays = () => {
  overlays.value.forEach(overlay => overlay.setMap(null));
};

const closeOverlay = (overlay) => {
  overlay.setMap(null);
};

const viewDetails = (id) => {
  router.push({ name: 'thunderDetail', params: { thunderId: id } });
};

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    kakao.maps.load(() => {
      initMap();
      // 초기 데이터 로드
      store.getThunderList();
    });
  } else {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_JS_API_KEY}`;
    script.addEventListener('load', () => {
      kakao.maps.load(() => {
        initMap();
        // 초기 데이터 로드
        store.getThunderList();
      });
    });
    document.head.appendChild(script);
  }
});

// thunderList가 변경되면 지도에 마커 추가
watch(() => store.thunderList, (newVal) => {
  addMarkers(newVal);
});

// regionPoint가 변경되면 지도 위치 업데이트
watch(() => store.regionPoint, (newVal) => {
  if (newVal.x && newVal.y) {
    const coords = new kakao.maps.LatLng(newVal.y, newVal.x);
    map.setCenter(coords);
  }
});
</script>

<style scoped>
#map {
  height: 600px;
  border-radius: 10px;
}

.buttons {
  display: flex;
  justify-content: end;
}

button {
  margin-top: 10px;
  font-weight: 600;
}

.btn {
  background-color: #F3D849;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 15px;
}

.map-btn {
  background-color: #E0E5E9;
  border: none;
  padding: 10px 20px;
  border-radius: 15px;
  margin-right: 10px;
}
</style>

<style>
.customoverlay {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background-color: white;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.customoverlay .customoverlay-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  text-align: left;
}

.customoverlay .close {
  align-self: flex-end;
  background: transparent;
  border: none;
  cursor: pointer;
}

.customoverlay .thunder-info {
  display: flex; /* 가로 배치 */
  align-items: center;
  flex-direction: row; /* 세로 배치 추가 */
}

.customoverlay .thunder-image {
  width: 70px; /* 원하는 크기로 조정 */
  height: 70px; /* 원하는 크기로 조정 */
  border-radius: 10px;
  margin-right: 20px;
  object-fit: cover;
}

.customoverlay .thunder-details {
  display: flex;
  flex-direction: column;
}

.customoverlay .category {
  font-size: 14px;
  color: #aaa;
  margin-bottom: 5px;
}

.customoverlay h3 {
  margin: 0;
  font-size: 20px;
  font-weight: bold;
}

.customoverlay p {
  margin: 2px 0;
  font-size: 14px;
  color: #666;
}

.customoverlay .date-location {
  display: block; /* 세로 배치 */
  margin-top: 10px;
}

.customoverlay .details {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #F3D849;
  border: none;
  border-radius: 50px; /* 둥근 모양 */
  cursor: pointer;
  align-self: center; /* 중앙에 배치 */
}
</style>
