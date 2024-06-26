import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const REST_THUNDER_API = 'http://localhost:8080/thunder/my';

export const useMyThunderStore = defineStore('mythunder', () => {

    const thunders = ref([]);

    const fetchRemainThunders = () => {
        const userId  = sessionStorage.getItem('loginUser');

        axios.get(`${REST_THUNDER_API}/remain`,{
            headers: {
                'loginuser': userId,
            },
            withCredentials: true // 세션 정보를 포함한 요청
        })
        .then((response) => {
            thunders.value = response.data;
        })
        .catch((error) => {
            alert('다시 시도해보세요.')
        })
    };

    const fetchPastThunders = () => {
        const userId  = sessionStorage.getItem('loginUser');

        axios.get(`${REST_THUNDER_API}/past`,{
            headers: {
                'loginuser': userId,
            },
            withCredentials: true // 세션 정보를 포함한 요청
        })
        .then((response) => {
            thunders.value = response.data;
        })
        .catch((error) => {
            alert('다시 시도해보세요.')
        })
    };
  
    return {
        fetchRemainThunders, fetchPastThunders, thunders
    };
})
