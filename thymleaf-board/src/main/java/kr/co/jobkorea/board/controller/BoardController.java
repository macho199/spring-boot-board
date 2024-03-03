package kr.co.jobkorea.board.controller;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import kr.co.jobkorea.board.model.Board;
import kr.co.jobkorea.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String boardController(Model model) {
        model.addAttribute("boards", boardService.getAll());
        
        return "board/list";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("board", new Board());
        return "board/write";
    }

    @PostMapping("/write")
    public String save(@RequestParam String title, @RequestParam String content) {
        if (title.equals("") || content.equals("")) {
            return "redirect:/board/write";
        }

        Board entity = Board.builder().title(title).content(content).regDt(Instant.now()).updateDt(Instant.now()).build();
        
        boardService.save(entity);
        return "redirect:/board";
    }

    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id, @RequestParam String title, @RequestParam String content) {
        if (id <= 0 || title.equals("") || content.equals("")) {
            return "redirect:/board";
        }

        Board entity = boardService.getById(id);
        entity.setTitle(title);
        entity.setContent(content);
        entity.setUpdateDt(Instant.now());
        boardService.save(entity);

        return "redirect:/board/" + id;
    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable Long id) {
        Board entity = boardService.getById(id);
        model.addAttribute("board", entity);

        return "board/view";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Board entity = boardService.getById(id);
        if (entity == null) {
            return "redirect:/board";
        }

        model.addAttribute("board", entity);

        return "board/write";
    }
}
