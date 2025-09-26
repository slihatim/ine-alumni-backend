import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/v1';

const getUserInfo = () => {
    return axios.get(API_URL + "/users/me", {
        headers: authHeader()
    });
};

const userService = {
    getUserInfo
};

export default userService;