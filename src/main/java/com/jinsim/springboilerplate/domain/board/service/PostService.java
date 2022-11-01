package com.jinsim.springboilerplate.domain.board.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.PostReqDto;
import com.jinsim.springboilerplate.domain.board.exception.PostNotFoundException;
import com.jinsim.springboilerplate.domain.board.repository.PostLikeRepository;
import com.jinsim.springboilerplate.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional(readOnly = true)
    public List<Post> findAllDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("id", postId));
    }

    @Transactional(readOnly = true)
    public List<Post> findByWriter(Account author) {
        return postRepository.findByWriter(author);
    }

    @Transactional(readOnly = true)
    public List<Post> findByTitle(String title) {
        return postRepository.findByTitleContains(title);
    }


    public Long create(PostReqDto.Create reqDto, Account account) {
        Post post = postRepository.save(reqDto.toEntity(account));
        return post.getId();
    }

    public void read(Long postId) {
        Post post = findById(postId);
        post.updateViewCount();
    }

    public void update(Long postId, PostReqDto.Update reqDto) {
        Post post = findById(postId);
        post.updatePost(reqDto.getTitle(), reqDto.getContent());
    }

    public void delete(Long postId) {
        Post post = findById(postId);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Integer countPostLike(Post post) {
        Integer count = postLikeRepository.countByPost(post);
        return count;
    }

}
