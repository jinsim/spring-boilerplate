package com.jinsim.springboilerplate.domain.board.repository;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 명명 규칙을 준수한다면, SQL문을 자동으로 만들어준다.
    List<Post> findAllByOrderByIdDesc(); // 최신 글을 위로 올리기 위해서 내림차순으로 정렬한다.
    List<Post> findByWriter(Account account);
    List<Post> findByTitleContains(String title);

    // 조회수 증가 코드. ModifiedDate를 변경시키지 않기 위함.
    @Modifying(clearAutomatically = true) // 연산 이후 영속성 컨텍스트를 clear 하도록 설정
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    void updateViewCount(@Param("postId") Long postId);

}
