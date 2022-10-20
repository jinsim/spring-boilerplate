package com.jinsim.springboilerplate.board.api;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.service.AccountService;
import com.jinsim.springboilerplate.account.service.AuthUser;
import com.jinsim.springboilerplate.board.domain.Post;
import com.jinsim.springboilerplate.board.dto.PostReqDto;
import com.jinsim.springboilerplate.board.dto.PostResDto;
import com.jinsim.springboilerplate.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResDto create(@RequestBody @Valid final PostReqDto.Create reqDto,
                             @AuthUser Account account) {
        Long postId = postService.create(reqDto, account);
        Post post = postService.findById(postId);
        return PostResDto.of(post);
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto read(@PathVariable final Long id) {
        Post post = postService.findById(id);
        return PostResDto.of(post);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (( @postService.findById(#id).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto update(@PathVariable final Long id, @RequestBody final PostReqDto.Update reqDto,
                             @AuthUser Account account) {
        postService.update(id, reqDto);
        Post post = postService.findById(id);
        return PostResDto.of(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable final Long id,
                       @AuthUser Account account) {
        postService.delete(id);
    }
}
