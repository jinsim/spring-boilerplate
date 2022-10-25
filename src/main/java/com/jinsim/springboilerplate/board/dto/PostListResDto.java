package com.jinsim.springboilerplate.board.dto;

import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostListResDto {

    private List<SinglePost> posts;
    private Integer count;

    @Getter
    public static class SinglePost{

        private Long postId;
        private String title;
        private Long writerId;
        private Integer commentsCount;

        public SinglePost(Long postId, String title, Long writerId, Integer commentsCount) {
            this.postId = postId;
            this.title = title;
            this.writerId = writerId;
            this.commentsCount = commentsCount;
        }

        public static SinglePost of(Post post) {
            return new SinglePost(post.getId(), post.getTitle(), post.getWriter().getId(), post.getCommentList().size());
        }
    }

    public static PostListResDto of(List<Post> postList) {
        return PostListResDto.builder()
                .posts(postList.stream().map(SinglePost::of).collect(Collectors.toList()))
                .count(postList.size())
                .build();
    }
}
