package com.rewards.service;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.repository.RewardsRepo;

@Service
public class RewardsService {

	@Autowired
	RewardsRepo repo;

	public void initInMemoryDB() {

		Connection connection = repo.getConnection();
		repo.createTable(connection);
		repo.insertTransactions(repo.getConnection());
	}

	public int getCustomerRewards(int customerId) {

		int rewardpoints = 0;

		rewardpoints = repo.getCustomerRewards(repo.getConnection(), customerId);

		return rewardpoints;
	}

}
