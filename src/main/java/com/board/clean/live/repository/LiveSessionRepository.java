package com.board.clean.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.clean.live.domain.LiveSession;

public interface LiveSessionRepository extends JpaRepository<LiveSession, Long>{

}
