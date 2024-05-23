<template>
    <div class="comment-user">
      <img :src="userImage" alt="user image" class="user-image" />
      <span class="user-name">{{ userName }}</span>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useUserStore } from '@/stores/user';
  
  const props = defineProps({
    userId: String
  });
  
  const userStore = useUserStore();
  const userName = ref('');
  const userImage = ref('');
  
  onMounted(async () => {
    await userStore.getUserById(props.userId);
    userName.value = userStore.user.name;
    userImage.value = userStore.user.image
      ? new URL(`/src/assets/userProfile/${userStore.user.image}`, import.meta.url).href
      : new URL(`/src/assets/userProfile/profile.png`, import.meta.url).href;
  });
  </script>
  
  <style scoped>
  .comment-user {
    display: flex;
    align-items: center;
  }
  
  .user-image {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    object-fit: cover;
    margin-right: 10px;
  }
  
  .user-name {
    font-size: 14px;
    font-weight: bold;
  }
  </style>
  