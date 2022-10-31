package com.jinsim.springboilerplate.domain.board.service;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.exception.AccountNotFoundException;
import com.jinsim.springboilerplate.domain.board.domain.Comment;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.dto.CommentReqDto;
import com.jinsim.springboilerplate.domain.board.exception.CommentNotFoundException;
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
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("commentId", commentId));
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

    public void update(CommentReqDto.Update reqDto, Long commentId) {
        Comment comment = findById(commentId);
        comment.updateComment(reqDto.getContent());
    }

    public void delete(Long commentId) {
        Comment comment = findById(commentId);
        commentRepository.delete(comment);
    }

    // 연관관계 편의 메소드를 사용한 코드
    @Transactional(readOnly = true)
    public List<Comment> findByPostObj(Long postId) {
        Post post = postService.findById(postId);
        List<Comment> commentList = post.getCommentList();
        log.info("post.getCommentList = {}", commentList);
        return commentList;
    }

    public Long createObj(CommentReqDto.Create reqDto, Long postId, Account account) {
        Post post = postService.findById(postId);
        Comment comment = commentRepository.save(reqDto.toEntity(post, account));
        post.addComment(comment);
        return comment.getId();
    }



}
