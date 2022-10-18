package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.board.domain.Post;
import lombok.*;

public class PostReqDto {

    @Getter
    @Builder // 생성자가 없는 경우, Package 레벨의 모든 인자를 받는 생성자를 생성한다.
    @AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 접근 막음
    public static class Create {
        private Long id;
        private String title;
        private String content;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
