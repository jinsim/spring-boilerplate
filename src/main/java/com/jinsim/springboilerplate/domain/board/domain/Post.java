package com.jinsim.springboilerplate.domain.board.domain;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA에서는 프록시를 생성을 위해서 반드시 기본 생성자 하나를 생성해야한다.
public class Post extends BaseTimeEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // post에만 persist 하면 되고, post만 삭제하면 댓글도 다 삭제된다.
    private List<Comment> commentList = new ArrayList<>();

//    @Column(columnDefinition = "integer default 0")
    private Integer viewCount;

    /**
     * insert 되기전 (persist 되기전) 실행된다.
     * */
    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }


    // 연관관계 편의 메소드
    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setPost(this);
    }


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
    public void updateViewCount(){
        this.viewCount += 1;
    }
}
