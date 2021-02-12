package me.sachingupta.blockchain.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommanConfig {

	@Bean
	public int difficultyLevel() {
		return 4;
	}
	
}
