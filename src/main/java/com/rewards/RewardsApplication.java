package com.rewards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rewards.service.RewardsService;

@SpringBootApplication
public class RewardsApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}
	
	@Autowired
	RewardsService service;
	@Override
	public void run (String... args) throws Exception {
		
		System.out.println("Init");
		service.initInMemoryDB();
	}

}
