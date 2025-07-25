package com.board.clean.global.aop;

import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingExceptionAspect {

	@Around("execution(* com.board.clean.post.service..*(..))")
	public Object logAndHandleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed(); // 정상 수행 시 그대로 반환
        } catch (Exception e) {
            log.error("FAILED in {}: {}", joinPoint.getSignature(), e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", "FAILED"));
        }
    }
}
