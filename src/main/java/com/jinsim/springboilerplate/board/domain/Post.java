package com.jinsim.springboilerplate.board.domain;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서는 프록시를 생성을 위해서 반드시 기본 생성자 하나를 생성해야한다.
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull(message = "제목은 필수로 입력되어야 합니다.")
    @Column(length = 500)
    private String title;

    @NotNull(message = "내용은 필수로 입력되어야 합니다.")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    @Builder
    public Post(String title, String content, Account writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
