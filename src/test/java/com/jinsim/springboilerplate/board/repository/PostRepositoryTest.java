package com.jinsim.springboilerplate.board.repository;

import com.jinsim.springboilerplate.domain.account.domain.Account;
import com.jinsim.springboilerplate.domain.account.repository.AccountRepository;
import com.jinsim.springboilerplate.domain.board.domain.Post;
import com.jinsim.springboilerplate.domain.board.repository.PostRepository;
import com.jinsim.springboilerplate.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest extends RepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        Account account1 = createAccount("test1@email.com", "test1", "encodedPassword1");
        Account account2 = createAccount("test2@email.com", "test2", "encodedPassword2");

        createPost("title1", "content1", account1);
        createPost("test2", "content2", account1);
        createPost("title3", "content3", account1);
        createPost("title4", "content4", account2);
        createPost("title5", "content5", account2);
    }

    @Test
    void findAllByOrderByIdDescTest() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        assertThat(posts.size()).isEqualTo(5);
    }

    @Test
    void findByWriterTest() {
        Account account = accountRepository.findByEmail("test1@email.com").get();
        List<Post> posts = postRepository.findByWriter(account);
        assertThat(posts.size()).isEqualTo(3);
    }

    @Test
    void findByTitleContainsTest() {
        String title = "title";
        List<Post> posts = postRepository.findByTitleContains(title);
        assertThat(posts.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    void updateViewCountTest() {
        Long postId = 1L;
        Post post = postRepository.findById(postId).get();
        System.out.println("post.get().getId() = " + post.getId());
        System.out.println("post.getViewCount() = " + post.getViewCount());
        postRepository.updateViewCount(postId);
        System.out.println("post.getViewCount() = " + postRepository.findById(postId).get().getViewCount());

    }

    private Account createAccount(String email, String name, String encodedPassword) {
        Account account = Account.builder()
                .email(email)
                .name(name)
                .encodedPassword(encodedPassword)
                .build();
        return accountRepository.save(account);
    }

    private Post createPost(String title, String content, Account writer) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
        return postRepository.save(post);
    }





}