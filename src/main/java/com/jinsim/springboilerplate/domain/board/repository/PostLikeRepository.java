package com.jinsim.springboilerplate.domain.board.repository;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.PostLike;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Integer countByPost(Post post);
    List<PostLike> findAllByWriter(Account account);
    boolean existsByWriterAndPost(Account account, Post post);
    Optional<PostLike> findByWriterAndPost(Account account, Post post);
}
