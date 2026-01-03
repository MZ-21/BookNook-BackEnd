package com.booknook_mz.BookNook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController//lets framework know this is a REST api class
public class BookNookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookNookApplication.class, args);
	}

	@GetMapping("/")
	public String apiRoot(){
		return "Hello World!";
	}

}
