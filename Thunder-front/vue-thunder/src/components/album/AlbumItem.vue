<template>
  <div class="item">
    <div class="polaroid">
      <img :src="photoImage" alt="photoImage" class="polaroid-img">
      <div class="photo-info">
        <span class="date">{{ formattedDate }}</span>
        <span class="title">{{ scheduleTitle }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { useMyThunderStore } from '@/stores/mythunder.js';

const props = defineProps({
  photo: Object
});

const thunderStore = useMyThunderStore();
const photoImage = ref('');

onMounted(() => {
  photoImage.value = new URL(`/src/assets/album/${props.photo.image}`, import.meta.url).href;
});

const schedule = computed(() => thunderStore.thunders.find(thunder => thunder.id === props.photo.scheduleId));
const scheduleTitle = computed(() => schedule.value ? schedule.value.title : 'Unknown Schedule');

const formattedDate = computed(() => {
  if (!schedule.value) return '';
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
  return new Date(schedule.value.dateTime).toLocaleDateString('ko-KR', options);
});
</script>

<style scoped>
.item {
  flex: 0 0 calc(33.33% - 2rem);
  /* margin-top: 2rem; */
  margin : 1rem 1rem;
  filter: grayscale(100%);
  /* transition: all 0.35s; */
}

.item:hover {
  filter: none;
  transform: scale(1, 1) rotate(0deg) !important;
}

.polaroid {
  background: #fff;
  padding: 1rem;
  box-shadow: 0 0.25rem 1rem rgba(0, 0, 0, 0.2);
  transition: all 0.35s;
  text-align: center;
  border-radius: 10px;
}

.polaroid:hover {
  transform: scale(1.05); /* 호버 시 폴라로이드 크기 증가 */
}

.polaroid-img {
  width: 300px; /* 고정된 가로 크기 */
  height: 400px; /* 조정된 세로 크기 */
  object-fit: cover; /* 이미지 비율 유지하며 잘라내기 */
  border-radius: 10px;
  margin-bottom: 10px;
  transition: transform 0.35s; /* 이미지 호버 시 크기 변경 효과 */
}

.polaroid-img:hover {
  /* transform: scale(1.1); 호버 시 이미지 크기 증가 */
}

.photo-info {
  text-align: center;
  margin-top: auto;
}

.date {
  font-size: 0.8rem;
  color: #666;
  display: block;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  text-decoration: underline;
  text-decoration-thickness: 5px;
  text-decoration-color: #f7d85f;
}
</style>
