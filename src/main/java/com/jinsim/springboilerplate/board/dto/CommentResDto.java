package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResDto {

    private Long postId;
    private String writerName;
    private String content;

    public static CommentResDto of(Comment comment) {
        return CommentResDto.builder()
                .postId(comment.getPost().getId())
                .writerName(comment.getWriter().getName())
                .content(comment.getContent())
                .build();
    }
}
