package com.jinsim.springboilerplate.domain.board.dto;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(name = "댓글 응답 객체", description = "댓글 생성, 수정 응답 객체입니다.")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResDto {

    @Schema(description = "아이디", example = "1")
    private Long commentId;
    @Schema(description = "게시글 아이디", example = "1")
    private Long postId;
    @Schema(description = "작성자명", example = "댓글작성자")
    private String writerName;
    @Schema(description = "내용", example = "테스트 댓글 내용입니다.")
    private String content;
    @Schema(description = "작성시각", example = "2022-11-05T08:42:02.654743")
    private LocalDateTime createDate;
    @Schema(description = "수정시각", example = "2022-11-06T08:42:02.654743")
    private LocalDateTime modifiedDate;

    public static CommentResDto of(Comment comment) {
        return CommentResDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .writerName(comment.getWriter().getName())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }
}
