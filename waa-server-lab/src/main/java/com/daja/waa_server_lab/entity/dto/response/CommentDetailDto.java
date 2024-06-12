package com.daja.waa_server_lab.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDetailDto {
    private Long id;

    private Long postId;

    private String content;

    private UserDetailDto author;

    private LocalDateTime createdAt;

    private String createdBy;
}
