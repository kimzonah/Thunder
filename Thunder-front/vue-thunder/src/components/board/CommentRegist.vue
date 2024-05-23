<template>
    <div class="comment-input">
      <input
        v-model="newComment"
        type="text"
        placeholder="댓글을 입력하세요"
        class="comment-input-field"
        @keyup.enter="addComment"
      />
      <button @click="addComment" class="comment-submit-button">댓글 달기</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRoute } from 'vue-router';
  import { useCommentStore } from '@/stores/comment';
  import { useUserStore } from '@/stores/user';
  
  const props = defineProps({
    board: Object,
  });
  
  const route = useRoute();
  const thunderId = route.params.thunderId;
  
  const newComment = ref('');
  const commentStore = useCommentStore();
  const userStore = useUserStore();
  
  const addComment = async () => {
    const boardId = props.board.id;
    if (newComment.value.trim() === '') return;
  
    try {
      await commentStore.addComment(thunderId, boardId, {
        content: newComment.value,
        user: {
          name: userStore.user.name,
          image: userStore.user.image
        }
      });
      newComment.value = '';
      // commentStore.fetchComments(thunderId, boardId); // 새 댓글 목록 불러오기
      window.location.reload();
    } catch (error) {
      alert('문제가 발생했습니다. 다시 시도해주세요.')
    }
  };
  </script>
  
  <style scoped>
  .comment-input {
    display: flex;
    align-items: center;
    margin-top: 20px;
  }
  
  .comment-input-field {
    flex-grow: 1;
    padding: 15px 10px 10px 15px;
    font-size: 14px;
    border: 1px solid #e0e0e0;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    border-radius: 15px;
    margin-right: 10px;
  }
  
  .comment-submit-button {
    padding: 10px 20px;
    font-size: 14px;
    /* color: #fff; */
    background-color: #ffcc00;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
    height: 45px;
    border: none;
    border-radius: 15px;
    cursor: pointer;
  }
  
  .comment-submit-button:hover {
    background-color: #f6c502;
  }
  </style>
  