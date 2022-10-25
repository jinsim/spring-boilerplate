package com.jinsim.springboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화. @WebMvcTest와 충돌난다.
@SpringBootApplication
public class SpringBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoilerplateApplication.class, args);
	}

}
