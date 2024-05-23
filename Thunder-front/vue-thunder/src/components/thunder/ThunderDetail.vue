<template>
  <div class="thunder-detail">
    <div class="header-image-container">
      <img :src="thunderImageUrl(thunderStore.thunder.image)" alt="Thunder Image" class="header-image" />
    </div>
    <div class="header-content">
      <h1 class="title">{{ thunderStore.thunder.title }}</h1>
      <p class="category">{{ thunderStore.thunder.category }}</p>
      <div class="date-location">
        <div>
          <img src="@/components/icons/common/calendar-icon.png" style="width: 17px; height: 17px; margin-right: 5px;">
          <span>{{ thunderStore.thunder.dateTime }}</span>
        </div>
        <div>
          <img src="@/components/icons/common/picker.png" style="width: 15px; height: 15px; margin-right: 5px;">
          <span>{{ trimmedAddress }}</span>
        </div>
      </div>
    </div>
    <div class="no-name">
      <div class="buttons">
        <button v-if="thunderStore.joinStatus === 0" class="status-btn join-btn"
        @click="joinThunder(thunderStore.thunder.id)">번개 참여하기</button>
        <button v-else-if="thunderStore.joinStatus === 1" class="status-btn already-btn">참여중</button>
        <button v-else-if="thunderStore.joinStatus === 2" class="status-btn wait-btn">승인 대기중</button>
        <button v-else>알 수 없음</button>
      </div>
      <div class="details">
        <div class="details-header">

          <img src="@/components/icons/common/megaphone.png" style="width: 20px; height: 20px; margin-right: 5px;">
          <h3>소개글</h3>
        </div>
        <p>{{ thunderStore.thunder.description }}</p>
      </div>
      <div class="people">
        <div class="members">
          <div class="members-header">
            <img src="@/components/icons/common/person.png" style="width: 20px; height: 20px; margin-right: 5px;">
            <h3>매니저 정보</h3>
          </div>
          <div class="member-list">
            <div class="member">
              <i class="icon-user"></i>
              <div class="manager">
                <div class="manager-profile">
                  <img :src="userImageUrl(userStore.user.image)" alt="">
                </div>
                <div class="manager-info">
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
          <div class="additional-info-header">
            <img src="@/components/icons/common/people.png" style="width: 20px; height: 20px; margin-right: 5px;">
            <h3>모집현황</h3>
          </div>
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
      router.push({ name: 'login' })
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
/* .thunder-detail {
  width: 100%;
  max-width: 1400px;
  margin: 10px auto;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
} */

.thunder-detail{
  padding: 10px 30px 30px 30px;
  background-color: white;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 2000px;
  margin: 20px auto 0px;
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
  border-radius: 20px;
  display: inline-block;
}

.buttons {
  display: flex;
  justify-content: end;
}

.buttons button:hover {
  background-color: #DDC12B;
}

.status-btn {
  margin-top: 10px;
  padding: 10px 30px;
  border: none;
  border-radius: 20px;
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
  align-items: center;
}

.date-location div {
  display: flex;
  justify-content: center;
  align-items: center;
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
}

.details-header {
  display: flex;
  align-items: center;
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

.members-header{
  display: flex;
  align-items: center;
}

.member-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 10px;
}

.member {
  display: flex;
  align-items: center;
  gap: 20px;
}

.manager {
  display: flex;
  align-items: center;
  gap: 60px; /* 간격을 넓혔습니다 */
}

.manager-profile {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
}

.manager-profile img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.manager-info {
  display: flex;
  flex-direction: column;
  gap: 10px; /* 간격 추가 */
}

.manager-name,
.manager-favor {
  display: flex;
  flex-direction: column;
  text-align: left;
  gap: 2px; /* 간격을 좁혔습니다 */
}

.manager-name h4,
.manager-favor h4 {
  margin-bottom: 2px; /* 간격을 좁혔습니다 */
}

.manager-name p,
.manager-favor p {
  margin-top: 2px; /* 간격을 좁혔습니다 */
}

.additional-info {
  width: 48%;
}

.additional-info-header{
  display: flex;
  align-items: center;
}

.info {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-top: 5px;
}

button {
  font-weight: 600;
}

</style>
