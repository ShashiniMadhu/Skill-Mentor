package com.skill_mentor.root.skill_mentor_root;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@EnableCaching
@SpringBootApplication
public class SkillMentorRootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillMentorRootApplication.class, args);
	}

	@Bean
	public CommandLineRunner testConnection(DataSource dataSource) {
		return args -> {
			try {
				Connection connection = dataSource.getConnection();
				System.out.println("✅ Database connection successful!");
				connection.close();
			} catch (Exception e) {
				System.out.println("❌ Database connection failed: " + e.getMessage());
			}
		};
	}

}
