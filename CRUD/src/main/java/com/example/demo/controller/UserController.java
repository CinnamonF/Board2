package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;

@Controller
public class UserController {

    @GetMapping("/board/login")
    public String loginForm() {
        return "board/login"; // → templates/board/login.html
    }
    
    // 회원가입 폼 GET
    @GetMapping("/board/signup")
    public String signupForm() {
        return "board/register";  // templates/board/register.html
    }

    // 회원가입 처리 POST
    @PostMapping("/board/signup")
    public String signupSubmit(@ModelAttribute User user) {
        // 여기서 유효성 검사 + 비밀번호 인코딩 등 처리
        return "redirect:/board/login"; // 가입 후 로그인으로 리디렉션
    }
}


