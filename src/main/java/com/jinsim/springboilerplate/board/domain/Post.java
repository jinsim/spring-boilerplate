package com.jinsim.springboilerplate.board.domain;

import com.jinsim.springboilerplate.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name = "account_id", updatable = false)
    private Account author;

    @Builder
    public Post(String title, String content, Account author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
