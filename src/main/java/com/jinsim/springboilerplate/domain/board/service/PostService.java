package com.jinsim.springboilerplate.domain.board.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.PostReqDto;
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

    @Transactional(readOnly = true)
    public List<Post> findAllDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
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

    public void update(Long postId, PostReqDto.Update reqDto) {
        Post post = findById(postId);
        post.updatePost(reqDto.getTitle(), reqDto.getContent());
    }

    public void delete(Long postId) {
        Post post = findById(postId);
        postRepository.delete(post);
    }

}
