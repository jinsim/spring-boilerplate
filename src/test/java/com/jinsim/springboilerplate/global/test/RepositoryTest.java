package com.jinsim.springboilerplate.global.test;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.jinsim.springboilerplate.global.config.P6SpyConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
// Spring extension을 등록
@DataJpaTest(showSql = false)
// Repository에 대한 Bean만 등록. showSql false를 통해 Spring Data JPA가 기본적으로 제공해주는 SQL 문 츨력 X
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// application.yml의 DB 설정과 연결한다. 기존 값 Replace.ANY 는 테스트용 인메모리를 강제로 사용한다는 의미
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
// 자동 환경 설정 클래스를 import한다. 내부의 클래스는 application.yml 에서 사용하고 있는 DataSource 를, 프록시한 객체로 만들어주는 역할을 한다.
@Disabled
// 테스트 비활성화
@Import(P6SpyConfiguration.class)
// 직접 커스텀한 p6spy 세팅을 import 시켜준다.
public class RepositoryTest {
}
