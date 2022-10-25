package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import lombok.*;

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
            private Long writerId;
            private String content;

            public SingleComment(Long writerId, String content) {
                this.writerId = writerId;
                this.content = content;
            }

            public static SingleComment of(Comment comment) {
                return new SingleComment(comment.getWriter().getId(), comment.getContent());
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

        private Long writerId;
        private List<SingleComment> comments;
        private Integer count;

        @Getter
        public static class SingleComment {
            private Long postId;
            private String content;

            public SingleComment(Long postId, String content) {
                this.postId = postId;
                this.content = content;
            }

            public static SingleComment of(Comment comment) {
                return new SingleComment(comment.getPost().getId(), comment.getContent());
            }
        }

        public static CommentListResDto.Account of(Long writerId, List<Comment> commentList) {
            return CommentListResDto.Account.builder()
                    .writerId(writerId)
                    .comments(commentList.stream().map(SingleComment::of).collect(Collectors.toList()))
                    .count(commentList.size())
                    .build();
        }
    }
}
