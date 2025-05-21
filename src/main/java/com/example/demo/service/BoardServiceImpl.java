package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.model.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
//    private final UserRepository userRepository;
//
//    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
//        this.boardRepository = boardRepository;
//        this.userRepository = userRepository;
//    }

    @Override
    public BoardDto.DetailResponse getBoardDetail(Long id) {
        Board board = boardRepository.findById(id);

        boardRepository.incrementViewCount(id);
        board.setViewCount(board.getViewCount() + 1);

        return new BoardDto.DetailResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUsername(),
                board.getUserId(),
                board.getViewCount(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    @Override
    public List<BoardDto.ListResponse> getBoardList(int page, int pageSize) {
        int offset = page * pageSize;

        List<Board> boards = boardRepository.findAll(offset, pageSize);

        return boards.stream()
                .map(board -> new BoardDto.ListResponse(
                        board.getId(),
                        board.getTitle(),
                        board.getUsername(),
                        board.getViewCount(),
                        board.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public int getTotalBoardCount() {
        return boardRepository.count();
    }

    @Override
    public Long createBoard(BoardDto.@Valid Request request, Long userId) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setUserId(userId);

        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    @Transactional
    @Override
    public BoardDto.DetailResponse updateBoard(Long id, BoardDto.@Valid Request request, Long userId) {
        Board board = boardRepository.findById(id);

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());

        Board updatedBoard = boardRepository.save(board);

        return new BoardDto.DetailResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUsername(),
                board.getUserId(),
                board.getViewCount(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    @Transactional
    @Override
    public boolean deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findById(id);

        return boardRepository.deleteById(id);
    }
}
