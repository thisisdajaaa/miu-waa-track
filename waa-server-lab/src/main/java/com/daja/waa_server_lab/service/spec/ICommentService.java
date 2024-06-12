package com.daja.waa_server_lab.service.spec;

import com.daja.waa_server_lab.entity.dto.request.CommentDto;
import com.daja.waa_server_lab.entity.dto.response.CommentDetailDto;

import java.util.List;
import java.util.Map;

public interface ICommentService {
    List<CommentDetailDto> findAll(Long postId, Map<String, String> filters);

    CommentDetailDto findById(Long id);

    CommentDetailDto add(Long postId, CommentDto postDto);

    CommentDetailDto delete(Long id);

    CommentDetailDto update(Long id, CommentDto updatedDto);

    CommentDetailDto findByPostIdAndCommentId(Long postId, Long commentId);
}
