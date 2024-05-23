<template>
  <div>
    <div>
      <div class="flex-container">
        <img src="@/components/icons/common/list.png" alt="">
        <h2>목록</h2>
        <p>총 {{ store.thunderList.length }}건</p>
      </div>
    </div>
    <div v-if="store.thunderList.length !== 0" class="thunder-list">
      <div v-for="thunder in paginatedThunders" :key="thunder.id" class="thunder-item">
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
    <div v-else>
      선택한 조건의 번개가 없습니다.
    </div>

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1" class="pagination-btn">이전</button>
      <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-btn">다음</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useThunderStore } from '@/stores/thunder';

const router = useRouter();
const store = useThunderStore();

const currentPage = ref(1);
const itemsPerPage = ref(4);

onMounted(() => {
  store.getThunderList();
});

// 이미지 URL 가져오는 함수
const getImageUrl = (imagePath) => {
  return imagePath ? new URL(`/src/assets/thunder/${imagePath}`, import.meta.url).href :
    new URL(`@/assets/thunder/thunderDefault.png`, import.meta.url).href;
};

const paginatedThunders = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return store.thunderList.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(store.thunderList.length / itemsPerPage.value);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const viewDetails = (id) => {
  router.push({ name: 'details', params: { id } });
};

const navigateToMap = () => {
  router.push({ name: 'map' });
};

// 함수로 addressName 처리

const getAddressName = (address) => {
  if (address) {
    return address.split(')')[0] + ')';
  }
  return '';
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

.description {
  margin: 5px 0;
  font-size: 14px;
  color: #888;
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
  /* 기본 이미지 */
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

.pagination {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.pagination button {
  margin: 0 5px;
  padding: 10px 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.flex-container {
  display: flex;
  align-items: center;
  gap: 10px;
}



/* .flex-container h2 {
  margin-right: 10px;
} */
</style>
