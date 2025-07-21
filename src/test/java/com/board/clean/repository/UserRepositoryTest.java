package com.board.clean.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.board.clean.user.domain.User;
import com.board.clean.user.domain.User.Role;
import com.board.clean.user.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	@DisplayName("User 저장 및 조회")
	public void saveAndFindUser()
	{
		
		User user = User.builder()
					.email("test@test.com")
					.password("qwerasdf")
					.name("테스트")
					.role(Role.USER)
					.build();
		
		User saveUser = userRepository.save(user);
		Optional<User> findUser = userRepository.findById(saveUser.getId());
		
		
		assertThat(findUser.get().getEmail()).isEqualTo("test@test.com");
		assertThat(findUser.get().getPassword()).isEqualTo("qwerasdf");
		assertThat(findUser.get().getRole()).isEqualTo(Role.USER);
		assertThat(findUser.get().getName()).isNotNull();
	}
}
