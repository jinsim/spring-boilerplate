package com.jinsim.springboilerplate.domain.board.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.domain.PostLike;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.exception.PostLikeException;
import com.jinsim.springboilerplate.domain.board.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndPost(Account account, Post post) {
        return postLikeRepository.existsByWriterAndPost(account, post);
    }

    @Transactional(readOnly = true)
    public Integer countPostLike(Post post) {
        Integer count = postLikeRepository.countByPost(post);
        return count;
    }

    @Transactional(readOnly = true)
    public List<PostLike> findByWriter(Account account) {
        return postLikeRepository.findByWriter(account);
    }

    @Transactional(readOnly = true)
    public List<Post> findLikePostList(List<PostLike> postLikeList) {
       return postLikeList.stream()
                .map(PostLike::getPost)
                .collect(Collectors.toList());
    }

    public void create(Long postId, Account account) {
        Post post = postService.findById(postId);
        if (isExistsByWriterAndPost(account, post)) {
            throw new PostLikeException("이미 좋아요 처리되었습니다.", "post");
        }
        PostLike postLike = PostLike.builder()
                .post(post)
                .account(account)
                .build();
        postLikeRepository.save(postLike);
    }

    public void delete(Long postId, Account account) {
        Post post = postService.findById(postId);

        PostLike postLike = postLikeRepository.findByWriterAndPost(account, post)
                .orElseThrow(() -> new PostLikeException("좋아요가 존재하지 않습니다.", "delete"));
        postLikeRepository.delete(postLike);
    }

}
