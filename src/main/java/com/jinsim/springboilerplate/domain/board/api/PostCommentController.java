package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.service.AuthUser;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.dto.CommentListResDto;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.dto.CommentResDto;
import com.jinsim.springboilerplate.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResDto createComment(@PathVariable final Long postId,
                                       @RequestBody @Valid final CommentReqDto.Create reqDto,
                                       @AuthUser Account account) {

        Long commentId = commentService.create(reqDto, postId, account);
        Comment comment = commentService.findById(commentId);
        return CommentResDto.of(comment);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CommentListResDto.Post readCommentList(@PathVariable final Long postId) {

        List<Comment> commentList = commentService.findByPost(postId);
        return CommentListResDto.Post.of(postId, commentList);
    }

    // 연관관계 편의 메소드로 작성한 코드
    @PostMapping("/obj")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResDto createCommentObj(@PathVariable final Long postId,
                                          @RequestBody @Valid final CommentReqDto.Create reqDto,
                                          @AuthUser Account account) {

        Long commentId = commentService.createObj(reqDto, postId, account);
        Comment comment = commentService.findById(commentId);
        return CommentResDto.of(comment);
    }

    @GetMapping("/obj")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentListResDto.Post readCommentListObj(@PathVariable final Long postId) {

        List<Comment> commentList = commentService.findByPostObj(postId);
        return CommentListResDto.Post.of(postId, commentList);
    }
}
