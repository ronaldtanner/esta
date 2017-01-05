package ch.semafor.esta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tar on 25.12.16.
 */
//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("ch.semafor.esta.core")
@ComponentScan(basePackages={
		"ch.semafor.esta.core",
		"ch.semafor.esta.web.controller"})
public class JqarApplication {
    public static void main(String[] args) {
		SpringApplication.run(JqarApplication.class, args);
	}
}
