package com.jinsim.springboilerplate.board.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.service.AuthUser;
import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import com.jinsim.springboilerplate.board.dto.*;
import com.jinsim.springboilerplate.board.service.CommentService;
import com.jinsim.springboilerplate.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts/{accountId}")
@RequiredArgsConstructor
public class AccountBoardController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/comments")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentListResDto.Account getAccountComments(@PathVariable final Long accountId, @AuthUser Account account) {
        List<Comment> commentList = commentService.findByWriter(account);
        return CommentListResDto.Account.of(account.getName(), commentList);
    }

    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public PostListResDto getAccountPosts(@PathVariable final Long accountId, @AuthUser Account account) {
        List<Post> postList = postService.findByWriter(account);
        return PostListResDto.of(postList);
    }
}
