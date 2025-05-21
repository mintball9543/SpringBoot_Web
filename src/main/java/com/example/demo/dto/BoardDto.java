package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 게시판 DTO 클래스
 * - 클라이언트와 서버 간의 데이터 전송에 사용됩니다.
 * - 입력 유효성 검사를 위한 어노테이션이 포함되어 있습니다.
 */

public class BoardDto {

    @Data
    // 게시글 등록/수정 요청 DTO
    public static class Request {
        @NotBlank(message = "제목은 필수입니다")
        @Size(max = 200, message = "제목은 최대 200자까지 입력 가능합니다")
        private String title;

        @NotBlank(message = "내용은 필수입니다")
        private String content;
    }

    @Data
    // 게시글 목록 응답 DTO
    public static class ListResponse {
        private Long id;
        private String title;
        private String username;
        private int viewCount;
        private LocalDateTime createdAt;

        public ListResponse(Long id, String title, String username, int viewCount, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.username = username;
            this.viewCount = viewCount;
            this.createdAt = createdAt;
        }
    }

    @Data
    // 게시글 상세 응답 DTO
    public static class DetailResponse {
        private Long id;
        private String title;
        private String content;
        private String username;
        private Long userId;
        private int viewCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DetailResponse(Long id, String title, String content, String username, Long userId, int viewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.userId = userId;
            this.viewCount = viewCount;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    // 날짜 포맷팅 유틸리티 메서드
    private static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
