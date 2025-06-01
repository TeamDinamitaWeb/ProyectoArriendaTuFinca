package co.edu.javeriana.tufinca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "co.edu.javeriana.tufinca.repositories")
@ComponentScan(basePackages = "co.edu.javeriana.tufinca")
@EntityScan(basePackages = "co.edu.javeriana.tufinca.entities")
public class TufincaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TufincaApplication.class, args);
	}
}