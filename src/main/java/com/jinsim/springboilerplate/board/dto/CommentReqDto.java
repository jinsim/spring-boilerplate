package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
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
