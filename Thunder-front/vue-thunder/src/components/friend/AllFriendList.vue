<template>
    <div>
        <div class="allfriend-header">
            <h2>전체 회원</h2>
            <p>{{ filteredFriends.length }}명</p>
        </div>
        <div class="nothing" v-if="filteredFriends.length === 0">
            전체 유저 중 검색 결과가 없습니다.
        </div>
        <div v-else>
            <div>
                <div class="friends-container">
                    <button class="nav-btn prev-btn" @click="prevSlide">‹</button>
                    <div class="cards-wrapper">
                        <div class="friend-card" v-for="friend in displayedFriends" :key="friend.id">
                            <div v-if="isRecruiting(friend.id)" class="status">
                                <img src="@/components/icons/common/thunder.png"
                                    style="width: 15px; height: 15px; margin-right: 5px;">
                                <div>번개 모집 중</div>
                            </div>
                            <div class="profile">
                                <img :src="imageUrl(friend.image)" alt="Profile Image" class="profile-img" />
                                <div class="profile-name">{{ friend.name }}</div>
                            </div>
                            <div class="actions">
                                <button v-if="isRecruiting(friend.id)" class="btn view-lightning"
                                    @click="goFriendThunder(friend.id)">번개 보러가기</button>
                                <button v-if="getFriendRelationStatus(friend.id) === 0" class="btn friend-action add-friend"
                                    @click="addFriend(friend.id)">친구 맺기</button>
                                <button v-else-if="getFriendRelationStatus(friend.id) === 1"
                                    class="btn friend-action active delete-friend" @click="removeFriend(friend.id)">친구 끊기</button>
                                <button v-else-if="getFriendRelationStatus(friend.id) === 2" class="btn friend-action wait-friend"
                                    style="pointer-events: none;">승인 대기중</button>
                                <button v-else-if="getFriendRelationStatus(friend.id) === 3" class="btn friend-action go-friend-manage"
                                    @click="goFriendManage">받은 요청 보기</button>
                                <button v-else class="btn friend-action">상태 알 수 없음</button>
                            </div>
                        </div>
                    </div>
                    <button class="nav-btn next-btn" @click="nextSlide">›</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useFriendStore } from '@/stores/friend';
import { useRouter } from 'vue-router';
import axios from 'axios';

const friendStore = useFriendStore();

const loginUserId = sessionStorage.getItem("loginUser");

// 온마운트 : 처음엔 검색어 없음(전체 조회)
onMounted(async () => {
    await friendStore.getAllFriendList('');
});

// 번개 보러가기
const router = useRouter();
const REST_FRIEND_API = `http://localhost:8080/friend`;
const goFriendThunder = (friendId) => {
    router.push({ name: 'friendThunder', params: { friendId: friendId } });
};

// 친구 맺기
const addFriend = async (friendId) => {
    try {
        await axios.post(`${REST_FRIEND_API}/${friendId}`, {}, { withCredentials: true }); // 쿠키를 포함한 요청
        friendStore.setFriendRelationStatus(friendId, 2); // 상태를 승인 대기중으로 업데이트
        alert("친구 요청을 보냈습니다.")
    } catch (error) {
        alert('문제가 발생했습니다. 다시 시도해주세요.')
    }
};

// 친구 끊기
const removeFriend = async (friendId) => {
    try {
        await axios.delete(`${REST_FRIEND_API}/${friendId}`, { withCredentials: true }); // 쿠키를 포함한 요청
        friendStore.setFriendRelationStatus(friendId, 0); // 상태를 친구 아님으로 업데이트
        alert("친구를 끊었습니다.")
    } catch (error) {
        alert('문제가 발생했습니다. 다시 시도해주세요.')
    }
};

// 받은 요청 보기
const goFriendManage = () => {
    router.push({ name: 'friendManage' });
};

// 친구가 번개를 모집중인지 확인
const isRecruiting = (friendId) => {
    return friendStore.friendRecruitingStatus[friendId] || false;
};

// 유저 프로필 사진 가져오기
const imageUrl = (friendImage) => {
    if (friendImage === null) {
        return new URL('@/assets/userProfile/profile.png', import.meta.url).href;
    }
    else {
        return new URL(`/src/assets/userProfile/${friendImage}`, import.meta.url).href;
    }
}

// 슬라이드 처리
const currentIndex = ref(0);
const itemsToShow = 3;

// 친구 리스트 필터링
const filteredFriends = computed(() => {
    return friendStore.allFriendList.filter(friend => friend.id !== loginUserId);
});

const displayedFriends = computed(() => {
    return filteredFriends.value.slice(currentIndex.value, currentIndex.value + itemsToShow);
});

const prevSlide = () => {
    if (currentIndex.value > 0) {
        currentIndex.value = Math.max(currentIndex.value - itemsToShow, 0);
    }
};

const nextSlide = () => {
    if (currentIndex.value < filteredFriends.value.length - itemsToShow) {
        currentIndex.value = Math.min(currentIndex.value + itemsToShow, filteredFriends.value.length - itemsToShow);
    }
};

// 친구 관계 상태 가져오기
const getFriendRelationStatus = (friendId) => {
    return friendStore.friendRelationStatus[friendId] ?? -1;
};
</script>

<style scoped>
.allfriend-header {
    display: flex;
    align-items: center;
    gap: 10px;
}

.nothing {
    text-align: center;
    align-content: center;
}

.friends-container {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0;
}

.nav-btn {
    background: none;
    border: none;
    font-size: 32px;
    cursor: pointer;
    padding: 0 8px;
}

.cards-wrapper {
    display: flex;
    justify-content: center;
    overflow: hidden;
    width: 90%;
}

.friend-card {
    flex: 0 0 calc(33.33% - 100px);
    padding: 24px;
    margin: 8px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: white;
    text-align: center;
    position: relative;
    box-sizing: border-box;
}

.profile {
    margin-top: 16px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.profile-pic {
    width: 80px;
    height: 80px;
    background-color: #e0e5e9;
    border-radius: 50%;
}

.profile-name {
    margin-top: 8px;
    font-size: 20px;
    font-weight: bold;
}

.actions {
    margin-top: 16px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.btn {
    width: 70%;
    padding: 12px;
    margin: 4px 0;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 520;
}

/* 번개보러가기 */
.view-lightning {
    background-color: white;
    border: 2px solid #F08989;
}

.view-lightning:hover{
    border: 2px solid #F08989;
    background-color: #FFF1F1;
}
/* 친구맺기 */
.add-friend {
    background-color: #F3D849;
}

.add-friend:hover{
    background-color: #DDC12B;
}

/* 친구끊기 */
.delete-friend {
    background-color: #E0E5E9;
}

.delete-friend:hover{
    background-color: #BCBCBC;
}
/* 승인 대기중 */
.wait-friend {
    background-color: #E0E5E9;
}

.wait-friend:hover{
    background-color: #BCBCBC;
}

/* 받은 요청 보기 */
.go-friend-manage{
    background-color: white;
    border: 2px solid #F3D849;
}

.go-friend-manage:hover{
    background-color: #FCF6D5;
    border: 2px solid #F3D849;
}

.profile-img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    margin-bottom: 15px;
}

.status {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 12px;
    display: flex;
    align-items: center;
    padding: 0px 5px;
}
</style>
