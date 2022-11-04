package com.jinsim.springboilerplate.domain.board.api;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.service.AuthUser;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.PostListResDto;
import com.jinsim.springboilerplate.domain.board.dto.PostReqDto;
import com.jinsim.springboilerplate.domain.board.dto.PostResDto;
import com.jinsim.springboilerplate.domain.board.service.PostLikeService;
import com.jinsim.springboilerplate.domain.board.service.PostService;
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
import java.util.List;

@Tag(name = "Post API", description = "게시글 API")
@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 작성", description = "게시글을 생성합니다.")
    @Parameters({
            @Parameter(name = "requestDto", description = "게시글 작성 요청 객체"),
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResDto createPost(@RequestBody @Valid final PostReqDto.Create reqDto,
                                 @AuthUser Account account) {
        Long postId = postService.create(reqDto, account);
        Post post = postService.findById(postId);
        Integer likeCount = postLikeService.countPostLike(post);
        boolean isLiked = postLikeService.isExistsByWriterAndPost(account, post);

        PostResDto resDto = PostResDto.of(post);

        resDto.setLike(likeCount, isLiked);
        return resDto;
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디" , example = "1"),
    })
    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto readPost(@PathVariable final Long postId, @AuthUser Account account) {
        postService.read(postId);
        Post post = postService.findById(postId);
        Integer likeCount = postLikeService.countPostLike(post);
        // 인증이 안된 유저인 경우, account에 null이 들어간다.
        boolean isLiked = postLikeService.isExistsByWriterAndPost(account, post);

        PostResDto resDto = PostResDto.of(post);
        resDto.setLike(likeCount, isLiked);
        return resDto;
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글을 ID 역순으로 조회합니다.")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public PostListResDto readPostList() {
        List<Post> postList = postService.findAllDesc();
        return PostListResDto.of(postList);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디" , example = "1"),
            @Parameter(name = "requestDto", description = "게시글 수정 요청 객체"),
    })
    @PutMapping("/{postId}")
    @PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResDto updatePost(@PathVariable final Long postId, @RequestBody final PostReqDto.Update reqDto
                                ,@AuthUser Account account) {
        postService.update(postId, reqDto);
        Post post = postService.findById(postId);

        Integer likeCount = postLikeService.countPostLike(post);
        boolean isLiked = postLikeService.isExistsByWriterAndPost(account, post);

        PostResDto resDto = PostResDto.of(post);

        resDto.setLike(likeCount, isLiked);

        return resDto;
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디" , example = "1"),
    })
    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@PathVariable final Long postId) {
        postService.delete(postId);
    }

    @Operation(summary = "게시글 좋아요 등록", description = "게시글의 좋아요를 등록합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디" , example = "1"),
    })
    @PostMapping("/{postId}/like")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createPostLike(@PathVariable final Long postId, @AuthUser Account account) {
        postLikeService.create(postId, account);
    }

    @Operation(summary = "게시글 좋아요 삭제", description = "게시글의 좋아요를 삭제합니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디" , example = "1"),
    })
    @DeleteMapping("/{postId}/like")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePostLike(@PathVariable final Long postId, @AuthUser Account account) {
        postLikeService.delete(postId, account);
    }
}
