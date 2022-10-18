package com.jinsim.springboilerplate.board.repository;

import com.jinsim.springboilerplate.account.domain.Account;
import com.jinsim.springboilerplate.account.repository.AccountRepository;
import com.jinsim.springboilerplate.board.domain.Post;
import com.jinsim.springboilerplate.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    void findByAuthorTest() {
        Account account = accountRepository.findByEmail("test1@email.com").get();
        List<Post> posts = postRepository.findByAuthor(account);
        assertThat(posts.size()).isEqualTo(3);
    }

    @Test
    void findByTitleTest() {
        String title = "title";
        List<Post> posts = postRepository.findByTitleContains(title);
        assertThat(posts.size()).isEqualTo(4);
    }

    private Account createAccount(String email, String name, String encodedPassword) {
        Account account = Account.builder()
                .email(email)
                .name(name)
                .encodedPassword(encodedPassword)
                .build();
        return accountRepository.save(account);
    }

    private Post createPost(String title, String content, Account author) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        return postRepository.save(post);
    }





}