import axios from 'axios';
import authHeader from './auth-header';

const API_URL = import.meta.env.VITE_API_URL;

export const register = (fullName, email, password, major, 
    graduationYear, phoneNumber, gender, country, city) => {
        return axios.post(API_URL +"/auth/signup", {
            fullName, email, password, major, 
            graduationYear, phoneNumber, gender, country, city
        }, 
        { validateStatus: function (status){ return status < 400}}
    )
}

export const login = (email, password) => {
  return axios.post(API_URL + "/auth/signin", {
    email, password
  }, {
    validateStatus: function (status) { 
      return status < 400; 
    }
  }).then((response) => {
    if (response.data.response) {
      localStorage.setItem("auth", JSON.stringify(response.data.response));
    }
    return response;
  });
}

export const logout = () => {
    localStorage.removeItem("auth");
    window.location.href = "/";
}

export const getAuthenticationState = async () => {
    try{
        const auth = JSON.parse(localStorage.getItem("auth"));

        if(!auth || !auth.token) return null;

        const response = await axios.get(API_URL + "/auth/validate", {headers: authHeader(),
            validateStatus: function (status){ return status < 400}
         });

         const token = auth.token;
         return {...response.data.response, token};

    } catch(error){
        localStorage.removeItem("auth");
        return null;
    }
}

export const verifyEmail = (otp) => {
    return axios.get(API_URL + "/email/verify", {headers: authHeader(), params: {token: otp} });
}

export const resendVerificationEmail = () => {
    return axios.get(API_URL + "/email/resend-verification", {headers: authHeader() });
}
