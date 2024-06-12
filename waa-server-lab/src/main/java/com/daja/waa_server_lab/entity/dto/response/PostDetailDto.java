package com.daja.waa_server_lab.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailDto {
    private Long id;

    private String title;

    private String content;

    private UserDetailDto author;

    private List<CommentDetailDto> comments;

    private LocalDateTime createdAt;

    private String createdBy;
}
