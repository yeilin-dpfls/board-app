import React, { useState } from 'react';
import axios from 'axios';

function Login({ onLoginSuccess }) {
  const [isRegister, setIsRegister] = useState(false);
  const [formData, setFormData] = useState({ username: '', password: '', nickname: '' });

  const handleSubmit = (e) => {
    e.preventDefault();
    const url = isRegister ? "http://15.164.97.42:30081/api/users/register" : "http://15.164.97.42:30081/api/users/login";
    axios.post(url, formData).then(res => {
      if (res.data === "success" && isRegister) {
        alert("🎉 가입 성공! 로그인 해주세요.");
        setIsRegister(false);
      } else if (res.data !== "fail" && !isRegister) {
        onLoginSuccess(res.data);
      } else { 
        alert("❌ 아이디/비밀번호를 확인해주세요."); 
      }
    });
  };

  return (
    <div className="card" style={{ maxWidth: '400px', margin: '0 auto' }}>
      <form onSubmit={handleSubmit}>
        <h2 style={{ textAlign: 'center' }}>{isRegister ? "✨ 환영합니다!" : "🔐 로그인"}</h2>
        <input placeholder="아이디" onChange={e => setFormData({...formData, username: e.target.value})} required />
        <input type="password" placeholder="비밀번호" onChange={e => setFormData({...formData, password: e.target.value})} required />
        {isRegister && <input placeholder="사용할 닉네임" onChange={e => setFormData({...formData, nickname: e.target.value})} required />}
        <button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '10px' }}>
          {isRegister ? "가입 완료" : "입장하기"}
        </button>
        <button type="button" className="btn-ghost" style={{ width: '100%' }} onClick={() => setIsRegister(!isRegister)}>
          {isRegister ? "이미 회원이신가요? 로그인하기" : "처음이신가요? 회원가입하기"}
        </button>
      </form>
    </div>
  );
}

export default Login;
