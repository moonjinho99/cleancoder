package com.board.clean.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.clean.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
