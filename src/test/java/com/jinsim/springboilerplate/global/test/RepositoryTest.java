package com.jinsim.springboilerplate.global.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // Spring extension을 등록
@DataJpaTest // Repository에 대한 Bean만 등록
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // application.yml의 DB와 연결
@Disabled // 테스트 비활성화
public class RepositoryTest {
}
