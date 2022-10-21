package com.jinsim.springboilerplate.board.repository;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.board.domain.Comment;
import com.jinsim.springboilerplate.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByWriter(Account account);
    List<Comment> findByPost(Post post);
}
