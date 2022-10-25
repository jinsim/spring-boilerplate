package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {

    private String title;
    private String content;
    private String writerName;

    public static PostResDto of(Post post) {
        return PostResDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writerName(post.getWriter().getName())
                .build();
    }
}
