package com.board.clean.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.clean.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
}
