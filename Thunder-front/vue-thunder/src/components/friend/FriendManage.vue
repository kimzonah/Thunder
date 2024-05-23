<template>
    <h2>나의 친구 요청 관리</h2>
    <div class="nothing" v-if="friendStore.friendRequestList.length === 0">
        받은 친구 요청이 없습니다.
    </div>
    <div v-else>
        <div v-for="friend in friendStore.friendRequestList" :key="friend.id" class="friend-card">
            <div class="profile">
                <img :src="imageUrl(friend.image)" alt="Profile Image" class="profile-icon" />
                <div class="profile-name">{{ friend.name }}</div>
            </div>
            <div class="action-buttons">
                <button class="accept-button" @click="addFriend(friend.id)">
                    <img src="@/components/icons/common/accept.png" alt="accept" />
                </button>
                <button class="decline-button" @click="rejectFriend(friend.id)">
                    <img src="@/components/icons/common/refuse.png" alt="decline" />
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useFriendStore } from '@/stores/friend';
import axios from 'axios';

const friendStore = useFriendStore();
const REST_FRIEND_API = `http://localhost:8080/friend`;

onMounted(() => {
    friendStore.getFriendRequestList();
})

// 유저 프로필 사진 가져오기
const imageUrl = (friendImage) => {
    if (friendImage === null) {
        return new URL('@/assets/userProfile/profile.png', import.meta.url).href;
    } else {
        return new URL(`/src/assets/userProfile/${friendImage}`, import.meta.url).href;
    }
};

// 친구 맺기
const addFriend = async (friendId) => {
    try {
        await axios.put(`${REST_FRIEND_API}/request/${friendId}`, {}, { withCredentials: true }); // 쿠키를 포함한 요청
        // 친구 요청 리스트에서 해당 요청 제거
        friendStore.friendRequestList = friendStore.friendRequestList.filter(friend => friend.id !== friendId);
        alert("요청을 수락했습니다.")
    } catch (error) {
    }
};

// 친구 거절
const rejectFriend = async (friendId) => {
    try {
        await axios.delete(`${REST_FRIEND_API}/request/${friendId}`, { withCredentials: true }); // 쿠키를 포함한 요청
        // 친구 요청 리스트에서 해당 요청 제거
        friendStore.friendRequestList = friendStore.friendRequestList.filter(friend => friend.id !== friendId);
        alert("요청을 거절했습니다.")
    } catch (error) {
        alert('문제가 발생했습니다. 다시 시도해주세요.')
    }
};
</script>

<style scoped>
.nothing {
    align-items: center;
    text-align: center;
}

.friend-card {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20px;
    margin: 10px 0;
    border-radius: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: white;
}

.profile {
    display: flex;
    align-items: center;
}

.profile-icon {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    /* Profile icon color */
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px;
}

.profile-icon::before {
    content: '👤';
    /* Placeholder icon */
    font-size: 24px;
    color: white;
}

.profile-name {
    font-size: 16px;
    font-weight: bold;
    color: #333;
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
