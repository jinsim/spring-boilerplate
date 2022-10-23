package com.jinsim.springboilerplate.board.service;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import com.jinsim.springboilerplate.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.board.repository.CommentRepository;
import com.jinsim.springboilerplate.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Comment> findByWriter(Account author) {
        return commentRepository.findByWriter(author);
    }

    @Transactional(readOnly = true)
    public List<Comment> findByPost(Long postId) {
        Post post = postService.findById(postId);
        return commentRepository.findByPost(post);
    }

    public Long create(CommentReqDto.Create reqDto, Long postId, Account account) {
        Post post = postService.findById(postId);
        Comment comment = commentRepository.save(reqDto.toEntity(post, account));
        return comment.getId();
    }

}
