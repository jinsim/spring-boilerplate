package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResDto {

    private Long postId;
    private String writerName;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public static CommentResDto of(Comment comment) {
        return CommentResDto.builder()
                .postId(comment.getPost().getId())
                .writerName(comment.getWriter().getName())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }
}