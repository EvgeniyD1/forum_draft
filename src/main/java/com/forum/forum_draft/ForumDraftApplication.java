package com.forum.forum_draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.forum.forum_draft"})
@EnableCaching
public class ForumDraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumDraftApplication.class, args);
	}

}
