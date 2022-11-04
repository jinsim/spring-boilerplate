package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class PostReqDto {

    @Schema(name = "게시글 작성 요청 객체", description = "게시글 작성 요청 객체입니다.")
    @Getter
    @Builder // 생성자가 없는 경우, Package 레벨의 모든 인자를 받는 생성자를 생성한다.
    @AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 접근 막음
    @NoArgsConstructor(access = AccessLevel.PROTECTED) // @RequestBody 시에 빈 생성자가 있어야 한다.
    public static class Create {

        @Schema(description = "제목", example = "테스트 게시글 제목")
        private String title;

        @Schema(description = "내용", example = "테스트 게시글 내용입니다.")
        private String content;

        public Post toEntity(Account writer) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .writer(writer)
                    .build();
        }
    }

    @Schema(name = "게시글 수정 요청 객체", description = "게시글 수정 요청 객체입니다.")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED) // @RequestBody 시에 빈 생성자가 있어야 한다.
    public static class Update {

        @Schema(description = "제목", example = "테스트 게시글 수정 제목")
        private String title;

        @Schema(description = "내용", example = "테스트 게시글 수정 내용입니다.")
        private String content;
    }
}
