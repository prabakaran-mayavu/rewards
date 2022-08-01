package com.rewards.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

@Repository
public class RewardsRepo {

	public Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connection = DriverManager.getConnection("jdbc:derby:memory:CustomerRewardsDB;create=true");

		} catch (Exception e) {
			System.out.println("RewardsRepo Connection Ex -->" + e.getMessage());
		}
		return connection;
	}

	public void createTable(Connection connection) {

		String createtbl = "CREATE TABLE CustomerRewards (CustomerId int, TransactionId int, Price double,Points int)";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(createtbl);
			statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void insertTransactions(Connection connection) {

		String insertdatasql = "INSERT INTO CustomerRewards values (?,?, ? ,?)";

		try {

			File file = ResourceUtils.getFile("classpath:CustomerTransactions.txt");
			InputStream in = new FileInputStream(file);

			Scanner myReader = new Scanner(in);
			while (myReader.hasNextLine()) {
				try {
					String data = myReader.nextLine();
					//System.out.println(data);
					String transactionDetails[] = data.split(":");
					int customerId = Integer.parseInt(transactionDetails[0]);
					int transactionId = Integer.parseInt(transactionDetails[1]);

					double price = Double.parseDouble(transactionDetails[2]);
					PreparedStatement statement = connection.prepareStatement(insertdatasql);
					statement.setInt(1, customerId);
					statement.setInt(2, transactionId);
					statement.setDouble(3, price);
					statement.setInt(4, calculateRewards(price));
					 statement.executeUpdate();

					statement.close();
				} catch (Exception e) {
				}

			}
			myReader.close();
		} catch (Exception e) {

		}

	}

	public int calculateRewards(double price) {

		int points = 0;
		try {

			int priceamount = (int) price;

			if (price >= 50 && price < 100) {
				return priceamount - 50;
			} else if (priceamount > 100) {
				return (2 * (priceamount - 100) + 50);
			}
		} catch (Exception e) {
		}
		return points;
	}

	public int getCustomerRewards(Connection connection, int customerId) {

		int points = 0;
		String selectsql = "Select Points from  CustomerRewards where  CustomerId = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(selectsql);
			statement.setInt(1, customerId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				points = points + rs.getInt("Points");
			}
			statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return points;
	}

}
