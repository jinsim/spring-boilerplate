package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class CommentReqDto {

    @Schema(name = "댓글 작성 요청 객체", description = "댓글 작성 요청 객체입니다.")
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Create {

        @Schema(description = "내용", example = "테스트 댓글 내용입니다.")
        private String content;

        public Comment toEntity(Post post, Account writer) {
            return Comment.builder()
                    .content(content)
                    .post(post)
                    .writer(writer)
                    .build();
        }
    }

    @Schema(name = "댓글 수정 요청 객체", description = "댓글 수정 요청 객체입니다.")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {

        @Schema(description = "내용", example = "테스트 댓글 수정 내용입니다.")
        private String content;
    }
}
