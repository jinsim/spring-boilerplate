package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentListResDto {

    @Schema(name = "게시글 댓글 목록 응답 객체", description = "게시글 댓글 목록 응답 객체입니다.")
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Post {

        @Schema(description = "게시글 아이디", example = "1")
        private Long postId;
        @Schema(description = "단일 댓글 객체 목록")
        private List<SingleComment> comments;
        @Schema(description = "게시글 댓글 수", example = "1")
        private Integer count;

        @Schema(name = "단일 댓글 객체", description = "게시글 댓글 목록 응답 객체에 포함되는 단일 댓글 객체입니다.")
        @Getter
        public static class SingleComment{

            @Schema(description = "아이디", example = "1")
            private Long commentId;
            @Schema(description = "작성자명", example = "댓글작성자")
            private String writerName;
            @Schema(description = "내용", example = "테스트 댓글 내용")
            private String content;
            @Schema(description = "작성시각", example = "2022-11-05T08:42:02.654743")
            private LocalDateTime createDate;
            @Schema(description = "수정시각", example = "2022-11-06T08:42:02.654743")
            private LocalDateTime modifiedDate;

            public SingleComment(Comment comment) {
                this.commentId = comment.getId();
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

    @Schema(name = "회원 댓글 목록 응답 객체", description = "회원 댓글 목록 응답 객체입니다.")
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Account {

        @Schema(description = "작성자명", example = "댓글작성자")
        private String writerName;
        @Schema(description = "단일 댓글 객체 목록")
        private List<SingleComment> comments;
        @Schema(description = "회원 댓글 수", example = "1")
        private Integer count;

        @Schema(name = "단일 댓글 객체", description = "회원 댓글 목록 응답 객체에 포함되는 단일 댓글 객체입니다.")
        @Getter
        public static class SingleComment {

            @Schema(description = "아이디", example = "1")
            private Long commentId;
            @Schema(description = "게시글 아이디", example = "1")
            private Long postId;
            @Schema(description = "게시글 제목", example = "1")
            private String postTitle;
            @Schema(description = "내용", example = "테스트 댓글 내용입니다.")
            private String content;
            @Schema(description = "작성시각", example = "2022-11-05T08:42:02.654743")
            private LocalDateTime createDate;
            @Schema(description = "수정시각", example = "2022-11-06T08:42:02.654743")
            private LocalDateTime modifiedDate;

            public SingleComment(Comment comment) {
                this.commentId = comment.getId();
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
