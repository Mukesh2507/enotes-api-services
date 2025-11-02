package in.mk.main.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ProjectConfig {
	
	
	@Bean
	public ModelMapper mapper() {
		
		return new ModelMapper();
		
	}
	

}
