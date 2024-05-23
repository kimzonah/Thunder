<template>
  <div class="top">
    <div class="search-container">
      <input type="text" class="search-bar" v-model="name" placeholder="닉네임으로 검색하세요." @keyup.enter="searchAll">
      <button class="search-btn" @click="searchAll">검색</button>
    </div>
    <div>
      <button class="manage-btn" @click="goFriendManage">친구 관리</button>
    </div>
  </div>
</template>
  
<script setup>
import { ref, watch } from 'vue';
import { useFriendStore } from '@/stores/friend';
import { useRouter } from 'vue-router';

const friendStore = useFriendStore();
const name = ref('');
const router = useRouter();

const searchAll = () => {
  friendStore.getAllFriendList(name.value);
  friendStore.getMyFriendList(name.value);
};

const goFriendManage = () => {
  router.push({ name: 'friendManage'});
};

</script>
  
<style scoped>
.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f9f9f9;
  border-bottom: 1px solid #ccc;
}

.search-container {
    display: flex;
    align-items: center;
    gap: 10px;
    
  }

.search-bar {
  padding: 10px 20px 10px 15px;
    border: none;
    border-radius: 50px;
    font-family: "Noto Sans KR", sans-serif; 
    font-size: 14px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    flex-grow: 1;
}

.search-button-container {
  margin-right: 10px; /* 추가된 스타일 */
}

.search-btn {
    background-color: #F3D849;
    border: none;
    padding: 10px 22px;
    cursor: pointer;
    border-radius: 20px;
    color: #333;
  }

  .search-btn:hover{
    background-color: #DDC12B;
    color: #333;
  }

.manage-btn {
  background-color: #E0E5E9;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 50px;
  color: #333;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.manage-btn:hover{
  background-color: #BCBCBC;
}

button{
    font-weight: 600;
  }
</style>
