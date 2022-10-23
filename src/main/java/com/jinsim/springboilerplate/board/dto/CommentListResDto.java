package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListResDto {

    private Long postId;
    private List<SingleComment> comments;

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

    public static CommentListResDto of(Long postId, List<Comment> commentList) {
        return CommentListResDto.builder()
                .postId(postId)
                .comments(commentList.stream().map(SingleComment::of).collect(Collectors.toList()))
                .build();
    }
}
