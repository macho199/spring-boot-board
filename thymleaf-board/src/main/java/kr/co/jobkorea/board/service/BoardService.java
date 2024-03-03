package kr.co.jobkorea.board.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.jobkorea.board.model.Board;
import kr.co.jobkorea.board.repository.BoardRepository;

@Service
public class BoardService {
    private BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public Iterable<Board> getAll() {
        return repository.findAll();
    }

    public Board getById(Long id) {
        return repository.findById(id).get();
    }

    public void save(Board board) {
        repository.save(board);
    }
}
