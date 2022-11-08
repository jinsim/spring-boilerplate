package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(name = "게시글 목록 응답 객체", description = "게시글 목록 응답 객체입니다.")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostListResDto {

    @Schema(description = "단일 게시글 객체 목록")
    private List<SinglePost> posts;
    @Schema(description = "게시글 수", example = "1")
    private Integer count;

    @Schema(name = "단일 게시글 객체", description = "게시글 목록 응답 객체에 포함되는 단일 게시글 객체입니다.")
    @Getter
    public static class SinglePost{

        @Schema(description = "아이디", example = "1")
        private Long postId;
        @Schema(description = "제목", example = "테스트 게시글 제목")
        private String title;
        @Schema(description = "작성자명", example = "게시글작성자")
        private String writerName;
        @Schema(description = "댓글 개수", example = "1")
        private Integer commentsCount;
        @Schema(description = "조회수", example = "1")
        private Integer viewCount;
        @Schema(description = "작성시각", example = "2022-11-05T08:42:02.654743")
        private LocalDateTime createDate;
        @Schema(description = "수정시각", example = "2022-11-06T08:42:02.654743")
        private LocalDateTime modifiedDate;

        public SinglePost(Post post) {
            this.postId = post.getId();
            this.title = post.getTitle();
            this.writerName = post.getWriter().getName();
            this.commentsCount = post.getCommentList().size();
            this.viewCount = post.getViewCount();
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
