package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.dto.CommentResDto;
import com.jinsim.springboilerplate.domain.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Comment API", description = "댓글 API")
@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 아이디", example = "1"),
            @Parameter(name = "requestDto", description = "댓글 수정 요청 객체"),
    })
    @PutMapping("/{commentId}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#commentId).getWriter().getEmail() " +
            "== principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentResDto updateComment(@PathVariable final Long commentId, @RequestBody @Valid final CommentReqDto.Update reqDto) {
        commentService.update(reqDto, commentId);
        Comment comment = commentService.findById(commentId);
        return CommentResDto.of(comment);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 아이디", example = "1")
    })
    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#commentId).getWriter().getEmail() " +
            "== principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteComment(@PathVariable final Long commentId) {
        commentService.delete(commentId);
    }

}
