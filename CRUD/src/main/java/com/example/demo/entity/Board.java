package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 게시글 ID (자동 증가)

	@Column(name = "image_filename")
	private String imageFilename; // 업로드한 파일 이름 저장

	@Column(nullable = false, length = 100)
	private String title; // 게시글 제목

	@Column(nullable = true, length = 500)
	private String content; // 게시글 내용

	@Column(nullable = false, length = 50)
	private String writer; // 작성자

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
	private java.util.List<Comment> comments = new java.util.ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdDate = new java.util.Date(); // 작성일

	// ✅ 직접 추가한 Getter & Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getImageFilename() {
	    return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
	    this.imageFilename = imageFilename;
	}

}
