package com.example.fileapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FileapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileapiApplication.class, args);
	}

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
