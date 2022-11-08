package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(name = "게시글 응답 객체", description = "게시글 생성, 조회, 수정 응답 객체입니다.")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {

    @Schema(description = "아이디", example = "1")
    private Long postId;
    @Schema(description = "제목", example = "테스트 게시글 제목")
    private String title;
    @Schema(description = "내용", example = "테스트 게시글 내용입니다.")
    private String content;
    @Schema(description = "작성자명", example = "게시글작성자")
    private String writerName;
    @Schema(description = "조회수", example = "1")
    private Integer viewCount;
    @Schema(description = "좋아요 개수", example = "1")
    private Integer likeCount;
    @Schema(description = "좋아요 유무", example = "true")
    private Boolean isLiked;
    @Schema(description = "작성시각", example = "2022-11-05T08:42:02.654743")
    private LocalDateTime createDate;
    @Schema(description = "수정시각", example = "2022-11-06T08:42:02.654743")
    private LocalDateTime modifiedDate;

    public static PostResDto of(Post post) {
        return PostResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerName(post.getWriter().getName())
                .viewCount(post.getViewCount())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    public void setLike(Integer likeCount, boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;

    }
}
