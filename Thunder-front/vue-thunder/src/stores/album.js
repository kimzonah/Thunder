import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

const REST_ALBUM_API = 'http://localhost:8080/album';

export const useAlbumStore = defineStore('album', () => {

    const createPhoto = async (formData, thunderId) => {
        const userId = sessionStorage.getItem('loginUser');
        try {
            await axios.post(`${REST_ALBUM_API}/${thunderId}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'loginUser' : userId
                },
                withCredentials: true // 쿠키를 포함한 요청
            });
            alert('사진이 등록되었습니다.');
        } catch (error) {
            alert('사진 등록에 실패 했습니다. 다시 시도해보세요.');
        }
    }

    const albums = ref([]);

    const fetchAlbum = async () => {
        try {
            const response = await axios.get('http://localhost:8080/album', {
                withCredentials: true
            });
            albums.value = response.data;
        } catch (error) {
            alert('앨범을 불러오는데 실패 했습니다. 다시 시도해보세요.');
        }
    };

    return {
        createPhoto, fetchAlbum, albums
    };
});
