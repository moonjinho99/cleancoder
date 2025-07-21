package com.board.clean.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.board.clean.post.domain.Post;
import com.board.clean.post.repository.PostRepository;
import com.board.clean.user.domain.User;
import com.board.clean.user.domain.User.Role;
import com.board.clean.user.repository.UserRepository;

@DataJpaTest
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@DisplayName("Post 엔티티 저장 및 조회 테스트")
	public void saveAndFindPost() {
		
		// given - User 엔티티 저장 (Post에 필요)
		User user = User.builder()
					.email("test@test.com")
					.password("qsaeqwaeq")
					.name("테스트")
					.role(Role.USER)
					.build();
		
		userRepository.save(user);
		
		Post post = Post.builder()
					.user(user)
					.title("테스트 제목")
					.content("테스트 내용")
					.code("int test = 0;")
					.postType(Post.PostType.NORMAL)
					.build();
		
		
		// when - 저장 후 조회
		Post savedPost = postRepository.save(post);
		Post findPost = postRepository.findById(savedPost.getId()).orElseThrow();
		
		assertThat(findPost.getTitle()).isEqualTo("테스트 제목");
		assertThat(findPost.getContent()).isEqualTo("테스트 내용");
		assertThat(findPost.getCode()).isEqualTo("int test = 0;");
		assertThat(findPost.getPostType()).isEqualTo(Post.PostType.NORMAL);
		assertThat(findPost.isActive()).isTrue();
		assertThat(findPost.getCreatedAt()).isNotNull();
		assertThat(findPost.getUpdatedAt()).isNotNull();
		assertThat(findPost.getUser().getName()).isEqualTo("테스트");		
	}
}
