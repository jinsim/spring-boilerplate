package com.jinsim.springboilerplate.domain.board.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.repository.CommentRepository;
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

    public void update(CommentReqDto.Update reqDto, Long id) {
        Comment comment = findById(id);
        comment.updateComment(reqDto.getContent());
    }

    public void delete(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
    }

}
