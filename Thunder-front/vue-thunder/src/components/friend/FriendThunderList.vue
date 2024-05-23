<template>
  <h2>{{ userStore.user.name }}님이 모집중인 번개</h2>
  <div class="thunder-container">
    <div class="thunder-list">
      <div v-for="thunder in friendStore.friendThunderList" :key="thunder.id" class="thunder-item">
        <img :src="getImageUrl(thunder.image)" alt="thunder image" class="thunder-image" />
        <div class="thunder-info">
          <div class="category">{{ thunder.category }}</div>
          <div class="item">
            <h4 class="title">{{ thunder.title }}</h4>
            <div class="location"><img src="@/components/icons/common/picker.png"
                style="width: 12px; margin-right: 5px;">{{ getAddressName(thunder.addressName) }}</div>
            <div class="location"><img src="@/components/icons/common/calendar-icon.png"
                style="width: 15px; height: 15px; margin-right: 5px;">{{ thunder.dateTime }}</div>
          </div>
        </div>
        <RouterLink :to="{ name: 'thunderDetail', params: { thunderId: thunder.id } }" class="thunder-detail-link"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useFriendStore } from '@/stores/friend';
import { useRoute } from 'vue-router';

const userStore = useUserStore();
const friendStore = useFriendStore();
const route = useRoute();


onMounted(() => {
  userStore.getUserById(route.params.friendId)
  console.log(route.params.friendId)
  friendStore.getFriendThunder(route.params.friendId)
});

const getImageUrl = (imagePath) => {
  return imagePath ? new URL(`/src/assets/thunder/${imagePath}`, import.meta.url).href :
    new URL(`@/assets/thunder/thunderDefault.png`, import.meta.url).href;
};

const getAddressName = (address) => {
  if (address) {
    return address.split(')')[0] + ')';
  }
}

</script>

<style scoped>
.thunder-container {
  padding: 20px;
}

.thunder-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.thunder-item {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 10px 20px;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  position: relative;
}

.thunder-item:hover {
  transform: translateY(-5px);
}

.thunder-image {
  width: 80px;
  height: 80px;
  border-radius: 10px;
  margin-right: 20px;
  object-fit: cover;
}

.thunder-info {
  flex-grow: 1;
}

.category {
  font-size: 14px;
  color: #aaa;
  margin: 0;
}


.title {
  margin: 0;
  font-size: 20px;
  font-weight: bold;
}

.location {
  display: flex;
  align-items: center;
  margin: 5px 0;
  font-size: 16px;
  color: #666;
}

.picker-icon {
  width: 12px;
  height: 12px;
  margin-right: 5px;
}

.thunder-detail-link {
  position: absolute;
  /* 수정: 부모 요소 내에서 절대 위치로 설정 */
  right: 20px;
  /* 오른쪽 여백 */
  width: 50px;
  height: 50px;
  border-radius: 30%;
  transition: background-color 0.2s;
  background-image: url('@/components/icons/common/detail-link.png');
  background-size: cover;
  background-position: center;
  cursor: pointer;
}

.thunder-detail-link:hover {
  background-image: url('@/components/icons/common/detail-link-active.png');
  /* hover 시 이미지 변경 */
}

.detail-arrow {
  width: 20px;
  height: 20px;
}
</style>