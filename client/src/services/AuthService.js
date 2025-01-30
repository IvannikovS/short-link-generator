import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const isAuthenticated = () => {
  const accessToken = localStorage.getItem('accessToken');
  return !!accessToken; 
};

export const logout = async (accessToken) => {
  try {
    await axios.get(`${API_URL}/logout`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    localStorage.removeItem('accessToken'); 
    localStorage.removeItem('refreshToken'); 
  } catch (error) {
    console.error('Ошибка при выходе:', error);
  }
};