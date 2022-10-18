package com.jinsim.springboilerplate.board.repository;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 명명 규칙을 준수한다면, SQL문을 자동으로 만들어준다.
    List<Post> findAllByOrderByIdDesc(); // 최신 글을 위로 올리기 위해서 내림차순으로 정렬한다.
    List<Post> findByAuthor(Account account);
    List<Post> findByTitle(String title);


}
