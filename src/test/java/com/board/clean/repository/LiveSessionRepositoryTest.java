package com.board.clean.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.board.clean.live.domain.LiveSession;
import com.board.clean.live.repository.LiveSessionRepository;
import com.board.clean.post.domain.Post;
import com.board.clean.post.repository.PostRepository;
import com.board.clean.user.domain.User;
import com.board.clean.user.domain.User.Role;
import com.board.clean.user.repository.UserRepository;

@DataJpaTest
public class LiveSessionRepositoryTest {

	@Autowired
	LiveSessionRepository liveSessionRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	@DisplayName("LiveSession 엔티티 저장 및 조회 테스트")
	public void saveAndFlushLiveSession() {
		
		User user = User.builder()
					.email("test@test.com")
					.password("qwerasdf")
					.name("테스트")
					.role(Role.USER)
					.build();
		
		userRepository.save(user);
		
		Post post = Post.builder()
					.user(user)
					.title("테스트 제목")
					.content("테스트 내용")
					.code("int i = 0;")
					.postType(Post.PostType.NORMAL)
					.build();
		
		postRepository.save(post);
				
		LiveSession liveSession = LiveSession.builder()
									.post(post)
									.startedAt(LocalDateTime.now())
									.isOpen(true)
									.build();
		
		LiveSession saveLiveSession = liveSessionRepository.save(liveSession);
		Optional<LiveSession> findLiveSession = liveSessionRepository.findById(saveLiveSession.getId());
		
		assertThat(findLiveSession.get().getPost().getId()).isEqualTo(post.getId());
		assertThat(findLiveSession.get().getCreatedAt()).isNotNull();
		assertThat(findLiveSession.get().getUpdatedAt()).isNotNull();
		assertThat(findLiveSession.get().getEndedAt()).isNull();
	}
	
	
}
