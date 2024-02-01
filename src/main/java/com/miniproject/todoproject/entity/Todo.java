package com.miniproject.todoproject.entity;

import java.util.ArrayList;
import java.util.List;

import com.miniproject.todoproject.dto.tododto.ToDoRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "todo")
public class Todo extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "contents", nullable = false, length = 500)
	private String contents;

	@Column(name = "complete", nullable = false)
	private boolean complete;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Comment> commentList = new ArrayList<>();

	public Todo(String title, String contents, User user) {
		this.title = title;
		this.contents = contents;
		this.user = user;
		this.complete = false;
	}

	public void updateComplete(boolean complete) {
		this.complete = complete;
	}

	public void addCommentList(Comment comment) {
		commentList.add(comment);
		comment.setTodo(this);
	}

	public void update(ToDoRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContents();
	}
}
