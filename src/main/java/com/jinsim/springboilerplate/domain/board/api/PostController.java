package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.service.AuthUser;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.PostListResDto;
import com.jinsim.springboilerplate.domain.board.dto.PostReqDto;
import com.jinsim.springboilerplate.domain.board.dto.PostResDto;
import com.jinsim.springboilerplate.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResDto createPost(@RequestBody @Valid final PostReqDto.Create reqDto,
                                 @AuthUser Account account) {
        Long postId = postService.create(reqDto, account);
        Post post = postService.findById(postId);
        return PostResDto.of(post);
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto readPost(@PathVariable final Long id) {
        Post post = postService.findById(id);
        post.updateViewCount();
        return PostResDto.of(post);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public PostListResDto readPostList() {
        List<Post> postList = postService.findAllDesc();
        return PostListResDto.of(postList);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @postService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto updatePost(@PathVariable final Long id, @RequestBody final PostReqDto.Update reqDto) {
        postService.update(id, reqDto);
        Post post = postService.findById(id);
        return PostResDto.of(post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @postService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@PathVariable final Long id) {
        postService.delete(id);
    }
}
