package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentListResDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Post {

        private Long postId;
        private List<SingleComment> comments;
        private Integer count;

        @Getter
        public static class SingleComment{
            private String writerName;
            private String content;
            private LocalDateTime createDate;
            private LocalDateTime modifiedDate;

            public SingleComment(Comment comment) {
                this.writerName = comment.getWriter().getName();
                this.content = comment.getContent();
                this.createDate = comment.getCreateDate();
                this.modifiedDate = comment.getModifiedDate();
            }

            public static SingleComment of(Comment comment) {
                return new SingleComment(comment);
            }
        }

        public static CommentListResDto.Post of(Long postId, List<Comment> commentList) {
            return CommentListResDto.Post.builder()
                    .postId(postId)
                    .comments(commentList.stream().map(SingleComment::of).collect(Collectors.toList()))
                    .count(commentList.size())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Account {

        private String writerName;
        private List<SingleComment> comments;
        private Integer count;

        @Getter
        public static class SingleComment {
            private Long postId;
            private String postTitle;
            private String content;
            private LocalDateTime createDate;
            private LocalDateTime modifiedDate;

            public SingleComment(Comment comment) {
                this.postId = comment.getPost().getId();
                this.postTitle = comment.getPost().getTitle();
                this.content = comment.getContent();
                this.createDate = comment.getCreateDate();
                this.modifiedDate = comment.getModifiedDate();
            }

            public static SingleComment of(Comment comment) {
                return new SingleComment(comment);
            }
        }

        public static CommentListResDto.Account of(String writerName, List<Comment> commentList) {
            return CommentListResDto.Account.builder()
                    .writerName(writerName)
                    .comments(commentList.stream().map(SingleComment::of).collect(Collectors.toList()))
                    .count(commentList.size())
                    .build();
        }
    }
}
