<template>
  <div class="thunder-detail">
    <div class="header-image-container">
      <img :src="thunderImageUrl(thunderStore.thunder.image)" alt="Thunder Image" class="header-image" />
    </div>
    <div class="header-content">
      <h1 class="title">{{ thunderStore.thunder.title }}</h1>
      <p class="category">{{ thunderStore.thunder.category }}</p>
      <div class="date-location">
        <span>📅 {{ thunderStore.thunder.dateTime }}</span>
        <span><img src="@/components/icons/common/picker.png" style="width: 15px; height: 15px; margin-right: 5px;">{{
          trimmedAddress }}</span>
      </div>
    </div>
    <div class="no-name">
      <button v-if="thunderStore.joinStatus === 0" class="status-btn join-btn"
        @click="joinThunder(thunderStore.thunder.id)">번개 참여하기</button>
      <button v-else-if="thunderStore.joinStatus === 1" class="status-btn already-btn">참여중</button>
      <button v-else-if="thunderStore.joinStatus === 2" class="status-btn wait-btn">승인 대기중</button>
      <button v-else>알 수 없음</button>
      <div class="details">
        <h3>소개글</h3>
        <p>{{ thunderStore.thunder.description }}</p>
      </div>
      <div class="people">
        <div class="members">
          <h3>매니저 소개</h3>
          <div class="member-list">
            <div class="member">
              <i class="icon-user"></i>
              <div class="manager">
                <div class="manager-profile">
                  <img :src="userImageUrl(userStore.user.image)" alt="">
                </div>
                <div>
                  <div class="manager-name">
                    <h4>이름</h4>
                    <p>{{ userStore.user.name }}</p>
                  </div>
                  <div class="manager-favor">
                    <h4>선호 운동</h4>
                    <p>{{ userStore.user.favorite }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="additional-info">
          <h3><img src="@/components/icons/common/person.png" style="width: 20px; height: 20px; margin-right: 5px;">모집
            현황</h3>
          <div class="info">
            <i class="icon-users"></i>
            <span>{{ thunderStore.joinNum }} / {{ thunderStore.thunder.scale }}명</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useThunderStore } from '@/stores/thunder';
import { useUserStore } from "@/stores/user";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const thunderStore = useThunderStore();
const userStore = useUserStore();

const trimmedAddress = ref('');
const REST_THUNDER_API = `http://localhost:8080/thunder`;

// 유저 프로필 사진 가져오기
const userImageUrl = (friendImage) => {
  if (friendImage === null) {
    return new URL('@/assets/userProfile/profile.png', import.meta.url).href;
  } else {
    return new URL(`/src/assets/userProfile/${friendImage}`, import.meta.url).href;
  }
}

// 번개 대표 이미지 가져오기
const thunderImageUrl = (thunderImage) => {
  if (thunderImage === null) {
    return new URL('@/assets/thunder/thunderDefault.png', import.meta.url).href;
  } else {
    return new URL(`/src/assets/thunder/${thunderImage}`, import.meta.url).href;
  }
}

onMounted(async () => {
  await thunderStore.getThunderDetail(route.params.thunderId); // 데이터를 가져올 때까지 기다림
  console.log(thunderStore.thunder.managerId); // 가져온 후에 콘솔 출력
  await thunderStore.checkJoinStatus(route.params.thunderId);
  // console.log(thunderStore.joinStatus)
  await thunderStore.countJoin(route.params.thunderId);
  if (thunderStore.thunder.managerId) {
    userStore.getUserById(thunderStore.thunder.managerId);
  }
});

watch(
  () => thunderStore.thunder.addressName,
  (newAddress) => {
    if (newAddress) {
      trimmedAddress.value = newAddress.split(')')[0] + ')';
    }
  },
  { immediate: true }
);

watch(
  () => thunderStore.thunder.managerId,
  (newManagerId) => {
    if (newManagerId) {
      userStore.getUserById(newManagerId);
    }
  }
);

watch(
  () => thunderStore.joinStatus,
  (newStatus) => {
    console.log("Join status changed:", newStatus);
  }
);

const joinThunder = async (thunderId) => {
  try {
    const userSession = sessionStorage.getItem('loginUser')
    if (!userSession) {
      alert('로그인이 필요합니다.')
      router.push({ name: 'home' })
    }
    else {

      await axios.post(`${REST_THUNDER_API}/join/${thunderId}`, {}, { withCredentials: true });
      await thunderStore.checkJoinStatus(thunderId); // 서버에서 상태를 다시 가져옴
      alert("번개 참여 신청 완료");
    }
  } catch (error) {
    console.error('There was an error!', error);
  }
}
</script>

<style scoped>
.thunder-detail {
  width: 100%;
  max-width: 1400px;
  margin: 10px auto;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.header {
  position: relative;
}

.header-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.header-content {
  padding: 20px;
  text-align: center;
}

.title {
  font-size: 24px;
  font-weight: bold;
}

.category {
  font-size: small;
  margin-top: 5px;
  background-color: #E0E5E9;
  padding: 5px 10px;
  border-radius: 10px;
  display: inline-block;
}

.status-btn {
  margin-top: 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  margin-left: auto;
}

.join-btn {
  background-color: #F3D849;
}

.already-btn,
.wait-btn {
  background-color: #E0E5E9;
  pointer-events: none;
}

.date-location {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 20px;
}

.date-time,
.location {
  display: flex;
  align-items: center;
  gap: 5px;
}

.details {
  padding: 20px;
  font-size: 16px;
  color: #666;
}

.no-name {
  max-width: 1200px;
  margin: auto;
  padding: 20px;
  border-top: 1px solid #eee;
}

.people {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.members {
  width: 48%;
}

.member-list {
  display: flex;
  gap: 20px;
  margin-top: 10px;
}

.member {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.additional-info {
  padding: 20px;
  width: 48%;
}

.info {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-top: 5px;
}

h3 {
  color: #000;
}

.manager-profile {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  margin-right: 80px;
}

.manager-profile img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.manager {
  display: flex;
  align-items: center;
}

.manager p {
  font-weight: 600px;
}

.manager h4 {
  margin-right: 10px;
}

.manager-name .manager-favor {
  display: inline;
}
</style>
