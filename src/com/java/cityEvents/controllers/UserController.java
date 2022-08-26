package com.java.cityEvents.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.cityEvents.model.User;

public class UserController {
	
	public static User signUser(Scanner scan) {
		
		// Asks user for its informations
		
		System.out.println("\n----------Signing In User----------\n");
		
		System.out.println("Type your name: ");
		String userName = scan.nextLine();
		
		System.out.println("\nType your CPF: ");
		String cpfToRegister = scan.nextLine();
		
		// Accepts only integer values
		System.out.println("\nType your age: ");
		Integer age = 0;
		try {
			age = Integer.parseInt(scan.nextLine());
		} catch(Exception e) {
			e.printStackTrace();
			// One more attempt
			System.out.println("\nType your age: ");
			age = Integer.parseInt(scan.nextLine());
		}
		
		System.out.println("\nType your city name: ");
		String city = scan.nextLine();
		
		// Creates empty list of events id's confirmed
		List<Integer> confirmedEvents = new ArrayList<>();
		
		// Returns new user
		return new User(userName, cpfToRegister, age, city, confirmedEvents);
	}
}
