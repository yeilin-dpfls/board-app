import React from 'react';

function BoardWrite({ title, setTitle, content, setContent, onSave }) {
  return (
    <div className="card">
      <h3>새 글 쓰기</h3>
      <div className="input-group">
        <input 
          placeholder="제목을 입력하세요" 
          value={title} 
          onChange={e => setTitle(e.target.value)} 
        />
      </div>
      <div className="input-group">
        <textarea 
          placeholder="내용을 입력하세요" 
          value={content} 
          onChange={e => setContent(e.target.value)} 
          rows="4" 
        />
      </div>
      <button onClick={onSave}>게시글 등록</button>
    </div>
  );
}

export default BoardWrite;