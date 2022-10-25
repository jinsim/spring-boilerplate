package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.dto.CommentResDto;
import com.jinsim.springboilerplate.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentResDto updateComment(@PathVariable final Long id, @RequestBody @Valid final CommentReqDto.Update reqDto) {
        commentService.update(reqDto, id);
        Comment comment = commentService.findById(id);
        return CommentResDto.of(comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @commentService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteComment(@PathVariable final Long id) {
        commentService.delete(id);
    }

}
