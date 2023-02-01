package org.nacol.cooltool;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;

@Log4j2
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CoolToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoolToolApplication.class, args);
	}


	@PostConstruct
	public void init() {
	}

}
