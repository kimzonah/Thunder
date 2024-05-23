<template>
  <div class="user-manage-container">
    <div class="user-list">
      <div v-if="manageStore.applyUsers.length === 0" class="no-users">
        가입 신청한 유저가 없습니다.
      </div>
      <div v-else>
        <div v-for="user in manageStore.applyUsers" :key="user.id" class="user-item">
          <img :src="getImageUrl(user.image)" alt="user image" class="user-image" />
          <span class="user-name">{{ user.name }}</span>
          <div class="action-buttons">
            <button class="accept-button" @click="acceptUser(user.id)">
              <img src="@/components/icons/common/accept.png" alt="accept" />
            </button>
            <button class="decline-button" @click="declineUser(user.id)">
              <img src="@/components/icons/common/refuse.png" alt="decline" />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useManageStore } from '@/stores/manage';

const manageStore = useManageStore();
const route = useRoute();

onMounted(() => {
  manageStore.fetchApplyUsers(route.params.thunderId);
});

const getImageUrl = (imagePath) => {
  return imagePath
    ? new URL(`/src/assets/userProfile/${imagePath}`, import.meta.url).href
    : new URL(`@/assets/userProfile/profile.png`, import.meta.url).href;
};

const acceptUser = (userId) => {
  manageStore.acceptUser(route.params.thunderId, userId);
};

const declineUser = (userId) => {
  manageStore.declineUser(route.params.thunderId, userId);
};
</script>

<style scoped>
.user-manage-container {
  padding: 20px;
}

.no-users {
  font-size: 18px;
  text-align: center;
  color: #666;
  padding: 20px;
}

.user-item {
  display: flex;
  align-items: center;
  padding: 20px;
  background-color: #fff;
  border-radius: 25px;
  margin-bottom: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 15px;
}

.user-name {
  flex-grow: 1;
  font-size: 18px;
}

.action-buttons {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-right: 10px;
}

.accept-button,
.decline-button {
  width: 30px;
  height: 30px;
  margin-right: 5px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  transition: background-color 0.3s;
}

.accept-button img,
.decline-button img {
  width: 35px;
  height: 35px;
}
</style>
