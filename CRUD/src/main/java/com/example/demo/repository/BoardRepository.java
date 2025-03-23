package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받으면 기본적인 CRUD 기능을 자동으로 제공
public interface BoardRepository extends JpaRepository<Board, Long> {
}
