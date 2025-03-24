package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	
	 private final PasswordEncoder passwordEncoder;
	 private final UserRepository userRepository;
	 
	// 생성자 주입
	    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
	        this.passwordEncoder = passwordEncoder;
	        this.userRepository = userRepository;
	    }

	 // 로그인 폼
	    @GetMapping("/login")  // ✅ 여기도 /login
	    public String loginForm() {
	        return "board/login"; // 로그인 폼 html 위치는 그대로
	    }
    
	 // 회원가입 폼
	    @GetMapping("/board/signup")
	    public String signupForm() {
	        return "board/register"; // → templates/board/register.html
	    }

	    // 회원가입 처리
	    @PostMapping("/board/register")
	    public String register(@ModelAttribute User user) {
	        System.out.println("▶ 회원가입 요청: " + user.getUsername());  // 이거 찍혀?
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRole("USER");
	        userRepository.save(user); // 실제 저장
	        return "redirect:/board/login";
	    }
}


