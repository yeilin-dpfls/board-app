import React, { useState } from 'react';
import axios from 'axios';

function BoardList({ boards, fetchBoards, currentUser }) {
  // 수정 모드 상태 관리
  const [editMode, setEditMode] = useState(null);
  const [editData, setEditData] = useState({ title: '', content: '' });

  const BOARD_API = "http://localhost:8080/api/boards";

  // [삭제] 기능
  const handleDelete = (id) => {
    if (window.confirm("🗑 정말 이 글을 삭제하시겠습니까?")) {
      axios.delete(`${BOARD_API}/${id}`)
        .then(() => {
          alert("삭제 완료! ✨");
          fetchBoards();
        })
        .catch(err => console.error("삭제 실패:", err));
    }
  };

  // [수정 저장] 기능
  const handleUpdate = (id) => {
    if (!editData.title || !editData.content) {
      alert("내용을 입력해주세요! ✍️");
      return;
    }
    axios.put(`${BOARD_API}/${id}`, editData)
      .then(() => {
        alert("수정되었습니다! ✅");
        setEditMode(null);
        fetchBoards();
      })
      .catch(err => console.error("수정 실패:", err));
  };

  // 수정 취소
  const cancelEdit = () => {
    setEditMode(null);
    setEditData({ title: '', content: '' });
  };

  return (
    <div className="card">
      <h3>📌 게시판 </h3>
      {boards.length === 0 ? (
        <p style={{ textAlign: 'center', color: '#888', padding: '20px' }}>
          아직 올라온 소식이 없어요. 첫 글을 남겨보세요! 😅
        </p>
      ) : (
        boards.map(b => (
          <div key={b.id} className="board-item">
            {editMode === b.id ? (
              // --- [수정 모드 UI] ---
              <div style={{ width: '100%' }}>
                <input 
                  value={editData.title} 
                  onChange={e => setEditData({ ...editData, title: e.target.value })} 
                  placeholder="수정할 제목"
                />
                <textarea 
                  value={editData.content} 
                  onChange={e => setEditData({ ...editData, content: e.target.value })} 
                  rows="4"
                  placeholder="수정할 내용"
                />
                <div style={{ marginTop: '10px' }}>
                  <button className="btn-primary" onClick={() => handleUpdate(b.id)}>💾 저장하기</button>
                  <button className="btn-ghost" onClick={cancelEdit}>❌ 취소</button>
                </div>
              </div>
            ) : (
              // --- [일반 보기 UI] ---
              <>
                <div className="board-content">
                  <span className="badge">👤 {b.writer?.nickname || '익명'}</span>
                  <h4 style={{ margin: '10px 0', fontSize: '1.2rem' }}>{b.title}</h4>
                  <p style={{ color: '#4b5563', whiteSpace: 'pre-wrap', lineHeight: '1.6' }}>
                    {b.content}
                  </p>
                </div>

                {/* 본인이 쓴 글일 때만 버튼 노출 */}
                {currentUser && currentUser.id === b.writer?.id && (
                  <div className="board-actions">
                    <button 
                      className="btn-ghost" 
                      onClick={() => { 
                        setEditMode(b.id); 
                        setEditData({ title: b.title, content: b.content }); 
                      }}
                    >
                      🛠 수정
                    </button>
                    <button 
                      className="btn-danger" 
                      onClick={() => handleDelete(b.id)}
                    >
                      🗑 삭제
                    </button>
                  </div>
                )}
              </>
            )}
          </div>
        ))
      )}
    </div>
  );
}

export default BoardList;