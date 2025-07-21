package com.board.clean.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.clean.post.domain.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>{

}
