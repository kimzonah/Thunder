<template>
  <div class="board-detail">
    <div class="board-header">
      <img :src="userImage" alt="user image" class="user-image" />
      <div class="board-info">
        <div class="user-name">{{ userName }}</div>
        <div class="board-date">
          {{ formatDate(board.regDate) }}
          <div v-if="board.userId === loginUserId" class="board-actions">
            <button @click="updateBoard" class="edit-button">수정</button>
            <button @click="deleteBoard(board.id)" class="delete-button">삭제</button>
          </div>
        </div>
      </div>
    </div>
    <div class="board-title">
      {{ board.title }}
    </div>
    <div class="board-content">
      <p>{{ board.content }}</p>
    </div>
  </div>
  <CommentRegist :board="board" @commentAdded="loadComments" />
  <CommentList :comments="comments" :board="board"/>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useCommentStore } from '@/stores/comment';
import { useUserStore } from '@/stores/user';
import { useBoardStore } from '@/stores/board';
import CommentRegist from './CommentRegist.vue';
import CommentList from './CommentList.vue';
import router from '@/router';

const route = useRoute();
const thunderId = route.params.thunderId;
const boardId = route.params.boardId;

const boardStore = useBoardStore();
const commentStore = useCommentStore();
const userStore = useUserStore();

const board = ref({});
const comments = ref([]);
const userName = ref('');
const userImage = ref('');
const loginUserId = userStore.loginUser.id;

const loadData = async () => {
  try {
    const boardData = await boardStore.fetchBoardDetail(thunderId, boardId);
    board.value = boardData;
    await loadUser(boardData.userId);
    loadComments();
  } catch (error) {
    // console.error('Failed to load data:', error);
  }
};

const loadUser = async (userId) => {
  try {
    await userStore.getUserById(userId);
    const userData = userStore.user
    userName.value = userData.name;

    userImage.value = userData.image
      ? new URL(`/src/assets/userProfile/${userData.image}`, import.meta.url).href
      : new URL(`/src/assets/userProfile/profile.png`, import.meta.url).href;

  } catch (error) {
    console.error('Failed to fetch user data:', error);
  }
};

const loadComments = async () => {
  try {
    comments.value = await commentStore.fetchComments(thunderId, boardId);
  } catch (error) {
    console.error('Failed to load comments:', error);
  }
};

onMounted(() => {
  loadData();
});

const formatDate = (dateString) => {
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false,
  };
  const date = new Date(dateString);
  return date.toLocaleString('sv-SE', options).replace(' ', 'T').replace('T', ' ').replace(/\//g, '-');
};

const updateBoard = () => {
  router.push({name:'boardUpdate', params: {thunderId: route.params.thunderId, boardId: board.value.id}});
};

const deleteBoard = async (boardId) => {
  try {
    await boardStore.deleteBoard(route.params.thunderId, boardId);
    router.push({ name: 'boardList', params: { thunderId: route.params.thunderId } });
  } catch (error) {
    console.error('Failed to delete board:', error);
  }
};
</script>

<style scoped>
.board-detail {
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin: 20px auto;
}

.board-header {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 15px;
}

.user-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 1px solid rgb(230, 230, 230);
}

.board-info {
  flex-grow: 1;
  margin-left: 10px;
}

.user-name {
  font-weight: bold;
  font-size: 16px;
}

.board-date {
  font-size: 12px;
  color: #6c757d;
  display: flex;
  align-items: center;
  gap: 5px;
}

.board-actions {
  display: flex;
  gap: 5px;
}

.edit-button,
.delete-button {
  background-color: transparent;
  border: none;
  color: rgb(169, 169, 169);
  cursor: pointer;
  font-size: 12px;
  padding: 0;
  margin-left: 5px;
}

.edit-button:hover,
.delete-button:hover {
  text-decoration: underline;
  color: #ffcc00;
}

.board-title {
  margin-top: 20px;
  margin-left: 10px;
  /* font-size: 24px; */
  font-weight: bold;
}

.board-content {
  font-size: 14px;
  color: #333;
  margin-top: 10px;
  margin-left: 10px;
}

.board-footer {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
}

.comment-count {
  font-size: 12px;
  color: #6c757d;
  cursor: pointer;
}

.comment-count:hover {
  text-decoration: underline;
  color: #ffcc00;
}
</style>
