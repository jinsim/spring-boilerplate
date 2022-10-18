package com.jinsim.springboilerplate.board.api;

import com.jinsim.springboilerplate.account.service.AccountService;
import com.jinsim.springboilerplate.board.domain.Post;
import com.jinsim.springboilerplate.board.dto.PostReqDto;
import com.jinsim.springboilerplate.board.dto.PostResDto;
import com.jinsim.springboilerplate.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResDto create(@RequestBody @Valid final PostReqDto.Create reqDto) {
        Long postId = postService.create(reqDto);
        Post post = postService.findById(postId);
        return PostResDto.of(post);
    }
}
