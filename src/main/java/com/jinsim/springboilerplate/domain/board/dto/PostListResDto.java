package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
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
        private String writerName;
        private Integer commentsCount;
        private LocalDateTime createDate;
        private LocalDateTime modifiedDate;

        public SinglePost(Post post) {
            this.postId = post.getId();
            this.title = post.getTitle();
            this.writerName = post.getWriter().getName();
            this.commentsCount = post.getCommentList().size();
            this.createDate = post.getCreateDate();
            this.modifiedDate = post.getModifiedDate();

        }

        public static SinglePost of(Post post) {
            return new SinglePost(post);
        }
    }

    public static PostListResDto of(List<Post> postList) {
        return PostListResDto.builder()
                .posts(postList.stream().map(SinglePost::of).collect(Collectors.toList()))
                .count(postList.size())
                .build();
    }
}
