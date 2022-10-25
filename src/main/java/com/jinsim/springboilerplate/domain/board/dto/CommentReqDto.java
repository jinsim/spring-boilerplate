package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import lombok.*;

public class CommentReqDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Create {
        private String content;

        public Comment toEntity(Post post, Account writer) {
            return Comment.builder()
                    .content(content)
                    .post(post)
                    .writer(writer)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {
        private String content;
    }
}
