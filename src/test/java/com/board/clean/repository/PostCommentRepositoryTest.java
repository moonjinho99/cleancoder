package com.board.clean.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.board.clean.post.domain.Post;
import com.board.clean.post.domain.PostComment;
import com.board.clean.post.repository.PostCommentRepository;
import com.board.clean.post.repository.PostRepository;
import com.board.clean.user.domain.User;
import com.board.clean.user.domain.User.Role;
import com.board.clean.user.repository.UserRepository;

@DataJpaTest
public class PostCommentRepositoryTest {

	@Autowired
	PostCommentRepository postCommentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@DisplayName("PostComment 저장 및 조회")
	public void saveAndFindPostComment()
	{
		User user = User.builder()
				.email("test@test.com")
				.password("qsaeqwaeq")
				.name("테스트")
				.role(Role.USER)
				.build();
	
		User saveUser = userRepository.save(user);
		
		Post post = Post.builder()
					.user(user)
					.title("테스트 제목")
					.content("테스트 내용")
					.code("int test = 0;")
					.postType(Post.PostType.NORMAL)
					.build();
		
		Post savePost = postRepository.save(post);
			
		PostComment postComment = PostComment.builder()
									.post(savePost)
									.user(saveUser)
									.content("테스트 댓글")
									.build();
	
		PostComment savePostComment = postCommentRepository.save(postComment);
		Optional<PostComment> findPostComment = postCommentRepository.findById(savePostComment.getId());
		
		assertThat(findPostComment.get().getContent()).isEqualTo("테스트 댓글");
		assertThat(findPostComment.get().getPost().getTitle()).isEqualTo("테스트 제목");
		assertThat(findPostComment.get().getUser().getName()).isEqualTo("테스트");
		assertThat(findPostComment.get().getPost().isActive()).isTrue();
		
	}
	
	
	
}
