package com.example.demo;

import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class BoardController {

    private final BoardRepository boardRepository;
    //private final KafkaTemplate<String, String> kafkaTemplate;

    // 1. [READ] 전체 목록 조회 (GET)
    @GetMapping
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 2. [READ] 특정 게시글 상세 조회 (GET)
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }

    // 3. [CREATE] 게시글 작성 (POST)
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        Board savedBoard = boardRepository.save(board);

        try {
	    
	    String writerName = (savedBoard.getWriter() != null) ? savedBoard.getWriter().getUsername() : "익명";
            String message = String.format("새 게시글 알림 - 제목: %s, 작성자: %s", 
                                            savedBoard.getTitle(), 
                                            writerName);
            
            //kafkaTemplate.send("board-events", message);
            System.out.println(">>> Kafka 전송 성공");
        } catch (Exception e) {
            System.err.println(">>> Kafka 전송 에러: " + e.getMessage());
        }

        return savedBoard;
    }

    // 4. [UPDATE] 게시글 수정 (PUT)
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board boardDetails) {
        
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));


        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());

        // 저장 (ID가 이미 존재하므로 JPA가 알아서 Update 쿼리를 날립니다)
        return boardRepository.save(board);
    }

    // 5. [DELETE] 게시글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        boardRepository.delete(board);
        return "게시글이 삭제되었습니다. ID: " + id;
    }
}
