import { useEffect, useState } from 'react';
import axios from 'axios';
import './App.css';
import Login from './components/Login';
import BoardList from './components/BoardList';
// 1. BoardWrite 컴포넌트를 불러옵니다.
import BoardWrite from './components/BoardWrite';

function App() {
  const [boards, setBoards] = useState([]);
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')));
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const fetchBoards = () => {
    axios.get("http://10.10.21.110:30080/api/boards")
      .then(res => setBoards(res.data))
      .catch(err => console.error("데이터 로딩 실패:", err));
  };

  useEffect(() => { fetchBoards(); }, []);

  const handleSave = () => {
    if (!title || !content) return alert("내용을 입력해주세요! ✍️");
    axios.post("http://10.10.21.110:30080/api/boards", { title, content, writer: user })
      .then(() => { 
        setTitle(''); 
        setContent(''); 
        fetchBoards(); 
      });
  };

  return (
    <div className="container">
      <h1> Board Project 💬</h1>
      
      {!user ? (
        <Login onLoginSuccess={(u) => { setUser(u); localStorage.setItem('user', JSON.stringify(u)); }} />
      ) : (
        <>
          <div className="user-bar">
            <span>👋 반갑습니다, <strong>{user.nickname}</strong>님!</span>
            <button className="btn-danger" onClick={() => { setUser(null); localStorage.removeItem('user'); }}>🚪 로그아웃</button>
          </div>
          
          {/* 2. 기존의 <div className="card"> 부분을 아래 컴포넌트로 교체합니다. */}
          <BoardWrite 
            title={title} 
            setTitle={setTitle} 
            content={content} 
            setContent={setContent} 
            onSave={handleSave} 
          />
        </>
      )}

      <BoardList boards={boards} fetchBoards={fetchBoards} currentUser={user} />
    </div>
  );
}

export default App;
