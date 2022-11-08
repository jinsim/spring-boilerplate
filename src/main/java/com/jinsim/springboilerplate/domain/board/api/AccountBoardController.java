package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.service.AuthUser;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.service.CommentService;
import com.jinsim.springboilerplate.domain.board.service.PostService;
import com.jinsim.springboilerplate.domain.board.dto.CommentListResDto;
import com.jinsim.springboilerplate.domain.board.dto.PostListResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account Board API", description = "회원 게시판 API")
@Slf4j
@RestController
@RequestMapping("/accounts/{accountId}")
@RequiredArgsConstructor
public class AccountBoardController {

    private final CommentService commentService;
    private final PostService postService;

    @Operation(summary = "댓글 목록 조회", description = "특정 회원이 작성한 댓글 목록을 조회합니다.")
    @Parameters({
            @Parameter(name = "accountId", description = "회원 아이디", example = "1")
    })
    @GetMapping("/comments")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentListResDto.Account getAccountComments(@PathVariable final Long accountId, @AuthUser Account account) {
        List<Comment> commentList = commentService.findByWriter(account);
        return CommentListResDto.Account.of(account.getName(), commentList);
    }

    @Operation(summary = "게시글 목록 조회", description = "특정 회원이 작성한 게시글 목록을 조회합니다.")
    @Parameters({
            @Parameter(name = "accountId", description = "회원 아이디", example = "1")
    })
    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated() and (( @accountService.findById(#accountId).getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public PostListResDto getAccountPosts(@PathVariable final Long accountId, @AuthUser Account account) {
        List<Post> postList = postService.findByWriter(account);
        return PostListResDto.of(postList);
    }
}
