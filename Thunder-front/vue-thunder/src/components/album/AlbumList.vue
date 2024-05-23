<template>
  <div class="album-container">
    <div v-if="albumStore.albums.length === 0" class="no-photos">
      등록된 사진이 없습니다.
    </div>
    <div v-else class="photo-gallery">
      <AlbumItem v-for="photo in albumStore.albums" :key="photo.id" :photo="photo" />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useAlbumStore } from '@/stores/album.js';
import { useMyThunderStore } from '@/stores/mythunder.js';
import AlbumItem from './AlbumItem.vue';

const albumStore = useAlbumStore();
const thunderStore = useMyThunderStore();

onMounted(async () => {
  await thunderStore.fetchPastThunders();
  await albumStore.fetchAlbum();
});
</script>

<style scoped>
.album-container {
  padding: 20px;
}

.photo-gallery {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  justify-items: center;
}

.no-photos {
  text-align: center;
  font-size: 16px;
  color: #888;
  margin-top: 20px;
}
</style>
