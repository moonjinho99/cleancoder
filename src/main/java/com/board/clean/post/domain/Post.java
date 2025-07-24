package com.board.clean.post.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.board.clean.user.domain.User;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    public enum PostType {
        NORMAL,
        LIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob // 큰 데이터 처리시에 사용 - DB 저장시에 CLOB 또는 BLOB 형식으로 저장
    private String content;

    @Lob
    private String code;
    
    @Column(nullable = false, length = 30)
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(nullable = false)
    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (postType == null) {
            postType = PostType.NORMAL;
        }
        isActive = true;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
}
