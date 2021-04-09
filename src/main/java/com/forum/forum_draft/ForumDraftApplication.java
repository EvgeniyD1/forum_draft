package com.forum.forum_draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.forum.forum_draft"})
public class ForumDraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumDraftApplication.class, args);
	}

}
