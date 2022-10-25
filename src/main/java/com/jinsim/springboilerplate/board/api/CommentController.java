package com.jinsim.springboilerplate.board.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.service.AuthUser;
import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.dto.CommentListResDto;
import com.jinsim.springboilerplate.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.board.dto.CommentResDto;
import com.jinsim.springboilerplate.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResDto createComment(@PathVariable final Long postId, @RequestBody @Valid final CommentReqDto.Create reqDto,
                                @AuthUser Account account) {

        Long commentId = commentService.create(reqDto, postId, account);
        Comment comment = commentService.findById(commentId);
        return CommentResDto.of(comment);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CommentListResDto readCommentList(@PathVariable final Long postId) {

        List<Comment> commentList = commentService.findByPost(postId);
        return CommentListResDto.of(postId, commentList);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentResDto updateComment(@PathVariable final Long postId, @PathVariable final Long id,
                                       @RequestBody @Valid final CommentReqDto.Update reqDto) {
        commentService.update(reqDto, id);
        Comment comment = commentService.findById(id);
        return CommentResDto.of(comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteComment(@PathVariable final Long postId, @PathVariable final Long id) {
        commentService.delete(id);
    }

}
