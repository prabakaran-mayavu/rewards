package com.rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.repository.RewardsRepo;
import com.rewards.service.RewardsService;

@RestController
@RequestMapping("/customer")
public class RewardsController {

	@Autowired
	RewardsService rewardService;

	@Autowired
	RewardsRepo rewardsRepo;

	@GetMapping("/rewards/{customerId}")
	public Object getCustomerRewards(@PathVariable(value = "customerId") int customerId) {

		int rewardpoints = 0;

		rewardpoints = rewardService.getCustomerRewards(customerId);

		return rewardpoints;
	}

}
