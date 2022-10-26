package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {

    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public static PostResDto of(Post post) {
        return PostResDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writerName(post.getWriter().getName())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
}
