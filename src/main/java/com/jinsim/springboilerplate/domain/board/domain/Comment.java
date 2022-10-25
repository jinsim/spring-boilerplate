package com.jinsim.springboilerplate.domain.board.domain;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서는 프록시를 생성을 위해서 반드시 기본 생성자 하나를 생성해야한다.
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotNull(message = "내용은 필수로 입력되어야 합니다.")
    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "post_id")
    private Post post;


    // 연관관계 편의 메소드
    protected void setPost(Post post) {
        this.post = post;
    }

    @Builder
    public Comment(String content, Account writer, Post post) {
        this.content = content;
        this.writer = writer;
        this.post = post;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}
