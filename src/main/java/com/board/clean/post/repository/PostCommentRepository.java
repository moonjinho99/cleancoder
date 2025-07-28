package com.board.clean.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.clean.post.domain.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>{

	List<PostComment> findByPostId(Long postId);

	
	@Query(value = "SELECT COUNT(id) FROM POST_COMMENTS WHERE POST_ID = :postId ", nativeQuery = true)
	Long countComment(@Param("postId") Long postId);
}
