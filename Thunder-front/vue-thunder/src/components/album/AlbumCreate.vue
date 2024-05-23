<template>
  <div>
    <header class="header">
      <div class="header-title">
        <h1>나의 사진첩</h1>
      </div>
        <div class="upload-section">
          <img src="../icons/common/upload.png" alt="upload icon" class="upload-icon">
          <label class="upload-label" for="file-upload">사진 등록</label>
        </div>
      <div class="header-controls">
        <select v-model="selectedThunderId" @change="updateSelectedThunder">
          <option v-for="thunder in myThunderStore.thunders" :key="thunder.id" :value="thunder.id">
            {{ thunder.title }} - {{ thunder.dateTime }}
          </option>
        </select>
        <button class="select-picture" @click="triggerFileUpload">사진 선택</button>
        <input id="file-upload" accept="image/*" type="file" @change="onFileChange" style="display: none;">
        <input type="text" placeholder="파일을 등록하세요" class="file-name" readonly>
        <button class="regist-picture" @click="registerPhoto">사진 등록하기</button>
      </div>
      <!-- <div class="hr-divider"></div> -->
    </header>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useMyThunderStore } from '@/stores/mythunder';
import { useAlbumStore } from '@/stores/album';
import { useRouter } from 'vue-router';

const router = useRouter();
const selectedThunderId = ref('');
const selectedThunder = ref(null); // 선택된 일정 객체
const selectedFile = ref(null);

const myThunderStore = useMyThunderStore();
const albumStore = useAlbumStore();

onMounted(() => {
  const userSession = sessionStorage.getItem('loginUser');
  // 로그인 되지 않은 경우
  if (!userSession) {
    alert('로그인이 필요합니다.');
    router.push({ name: 'home' });
  }

  myThunderStore.fetchPastThunders();
});

const onFileChange = (event) => {
  selectedFile.value = event.target.files[0];
  document.querySelector('.file-name').value = event.target.files[0].name;
};

const updateSelectedThunder = () => {
  selectedThunder.value = myThunderStore.thunders.find(thunder => thunder.id === selectedThunderId.value);
};

const registerPhoto = async () => {
  if (!selectedThunderId.value || !selectedFile.value) {
    alert('일정과 사진을 선택하세요.');
    return;
  }

  const formData = new FormData();
  formData.append('file', selectedFile.value);

  try {
    await albumStore.createPhoto(formData, selectedThunderId.value);
    window.location.reload();
    // alert('사진이 성공적으로 등록되었습니다!');
  } catch (error) {
    alert('사진 등록에 실패했습니다.');
  }
};

const triggerFileUpload = () => {
  document.getElementById('file-upload').click();
};
</script>

<style scoped>
.header {
  padding: 20px;
  background-color: #f9f9f9;
  border-bottom: 2px solid #eaeaea;
}

.header-title {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.header-title h1 {
  font-size: 24px;
  margin: 0;
  color: #333333;
  font-weight: bold;
  margin-right: 20px;
}

.upload-section {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.upload-icon {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.upload-label {
  font-size: 16px;
  font-weight: 800;
}

.header-controls {
  display: flex;
  align-items: center;
  margin-top: 20px;
}

.header-controls select {
  width: 250px;
  height: 40px;
  margin-right: 10px;
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #dddddd;
  border-radius: 15px;
}

.header-controls .file-name {
  margin-left: 10px;
  width: 200px;
  padding: 12px;
  font-size: 12px;
  border: 1px solid #dddddd;
  border-radius: 15px;
  margin-right: 10px;
}

.select-picture {
  background-color: #E0E5E9;
  border: none;
  padding: 10px 16px;
  font-size: 14px;
  border-radius: 15px;
  cursor: pointer;
}

.select-picture:hover {
  background-color: #d9dbdc;
}

.regist-picture {
  background-color: #F3D849;
  border: none;
  padding: 10px 16px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 15px;
  cursor: pointer;
  margin-left: auto;
}

.regist-picture:hover {
  background-color: #eec90e;
}

</style>
