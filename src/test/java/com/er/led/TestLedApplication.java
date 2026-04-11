package com.er.led;

import org.springframework.boot.SpringApplication;

public class TestLedApplication {

	public static void main(String[] args) {
		SpringApplication.from(LedApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
