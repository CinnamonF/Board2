package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/board/login")
	public String login() {
		return "board/login"; // 이 파일이 templates/board/login.html
	}

	// 게시글 목록 조회
	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boardList = boardRepository.findAll();
		model.addAttribute("boardList", boardList);
		return "board/list"; // list.html 템플릿과 연결
	}

	// 게시글 작성 폼
	@GetMapping("/write")
	public String writeForm(Model model) {
		model.addAttribute("board", new Board());
		return "board/write"; // write.html 템플릿과 연결
	}

	// 게시글 저장
	@PostMapping("/write")
	public String write(@ModelAttribute Board board, @RequestParam("imageFile") MultipartFile imageFile)
			throws IOException {

		if (board.getContent() == null || board.getContent().trim().isEmpty()) {
			board.setContent("내용 없음");
		}

		// 이미지 파일 저장

		if (!imageFile.isEmpty()) {
			String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
			String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
			File uploadPath = new File(uploadDir);
			if (!uploadPath.exists()) {
				uploadPath.mkdirs(); // 폴더 없으면 생성
			}

			imageFile.transferTo(new File(uploadDir + filename));
			board.setImageFilename(filename); // DB에 파일명 저장

		}

		boardRepository.save(board);
		return "redirect:/board/list";
	}

	// 게시글 수정 처리
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @ModelAttribute Board updatedBoard) {
		Board existingBoard = boardRepository.findById(id).orElse(null);

		if (existingBoard != null) {
			existingBoard.setTitle(updatedBoard.getTitle());
			existingBoard.setContent(updatedBoard.getContent());
			existingBoard.setWriter(updatedBoard.getWriter());

			boardRepository.save(existingBoard);
		}

		return "redirect:/board/list"; // 수정 후 목록으로 이동
	}

	// 게시글 상세 조회
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model) {
		Board board = boardRepository.findById(id).orElse(null);
		model.addAttribute("board", board);

		List<Comment> commentList = commentRepository.findByBoardId(id);
		model.addAttribute("commentList", commentList);

		model.addAttribute("comment", new Comment()); // 댓글 입력용
		return "board/view";
	}

	@PostMapping("/comment/{boardId}")
	public String addComment(@PathVariable Long boardId, @ModelAttribute Comment comment) {
		Board board = boardRepository.findById(boardId).orElse(null);
		if (board != null) {
			comment.setBoard(board);
			commentRepository.save(comment);
		}
		return "redirect:/board/view/" + boardId;
	}

	// 게시글 삭제
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardRepository.deleteById(id);
		return "redirect:/board/list"; // 삭제 후 목록 페이지로 이동
	}

	// 게시글 수정 폼 (edit.html 페이지로 이동)
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Board board = boardRepository.findById(id).orElse(null);
		model.addAttribute("board", board);
		return "board/edit"; // edit.html 템플릿과 연결
	}
}
