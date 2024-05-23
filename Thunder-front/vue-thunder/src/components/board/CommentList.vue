<template>
  <div class="board-comments">
    <h2 class="comments-title">댓글</h2>
    <!-- <div v-if="loading" class="loading">Loading...</div> -->
    <div v-if="comments.length === 0" class="no-comments">
      댓글이 없습니다.
    </div>
    <div v-else>
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <CommentUser :userId="comment.userId" />
        <div class="comment-header">
          <span class="comment-user-name">{{ comment.userId }}</span>
          <!-- <span class="comment-date">{{ formatDate(comment.regDate) }}</span> -->
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <div v-if="comment.userId === userStore.loginUser.id" class="board-actions">
          <button @click="deleteComment(comment.id)" class="delete-button">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { useCommentStore } from '@/stores/comment';
import CommentUser from './CommentUser.vue';

const userStore = useUserStore();
const commentStore = useCommentStore();

const props = defineProps({
  comments: Array,
  board: Object
});

onMounted(() => {

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

const deleteComment = (commentId) => {
  commentStore.deleteComment(props.board.scheduleId, props.board.id, commentId);
}
</script>

<style scoped>
.comments-title {
  margin-left: 5px;
  font-size: 18px;
  margin-bottom: 20px;
}

.loading {
  text-align: center;
  font-size: 16px;
  color: #666;
}

.no-comments {
  text-align: center;
  font-size: 16px;
  color: #888;
  margin-bottom: 20px;
}

.comment-item {
  margin-bottom: 20px;
  padding: 20px 20px 10px 20px;
  background-color: #ebebeb;
  border-radius: 8px;
  border: 1px solid #ebebeb;
  position: relative; /* 추가 */
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.comment-user-name {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.comment-date {
  font-size: 12px;
  color: #888;
}

.comment-content {
  font-size: 14px;
  color: #555;
}

.board-actions {
  position: absolute; /* 추가 */
  right: 10px; /* 추가 */
  top: 10px; /* 추가 */
}

.delete-button {
  background-color: transparent;
  border: none;
  margin-right: 10px;
  color: rgb(169, 169, 169);
  cursor: pointer;
  font-size: 12px;
  padding: 0;
}

.delete-button:hover {
  text-decoration: underline;
  color: #ffcc00;
}
</style>
