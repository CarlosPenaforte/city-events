package com.java.cityEvents;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import com.java.cityEvents.controllers.EventController;
import com.java.cityEvents.controllers.FileModelController;
import com.java.cityEvents.controllers.UserController;
import com.java.cityEvents.model.Event;
import com.java.cityEvents.model.FileModel;
import com.java.cityEvents.model.User;

public class App {

	public static void main(String[] args) throws IOException, ParseException {
		// Reads file and create a model to help managing data
		FileModel file= FileModelController.readFile("./src/com/resources/events.data");
		
		// Start program
		Scanner scan = new Scanner(System.in);
		System.out.println("Start Program? (Y/n): ");
		String keepAlive = scan.nextLine();
		
		String cpfToAuth = "";
		if(!keepAlive.equals("n")) {
			// Authenticating user
			System.out.println("\n----------Program Started----------\n");
			System.out.println("Are you already registered? (Y/n): ");
			String registered = scan.nextLine();
			
			if(!registered.equals("n")) {
				// Check if CPF is registered
				System.out.println("Type your CPF: ");
				cpfToAuth = scan.nextLine();
				// Create a loop for authentication attempts
				while(!file.getRegisteredCPFs().contains(cpfToAuth)) {
					System.out.println("\n!!!CPF NOT FOUND!!!");
					System.out.println("\nDo you want to try again? (Y/n): ");
					String response = scan.nextLine();
					
					if(!response.equals("n")) {
						System.out.println("Type your CPF: ");
						cpfToAuth = scan.nextLine();
					} else {
						// Starts signing in user if give up authenticating
						User newUser = UserController.signUser(scan);
						FileModelController.registerUser(file, newUser);
						cpfToAuth = newUser.getCpf();
					}
				}
			} else {
				// Sign in new user
				User newUser = UserController.signUser(scan);
				FileModelController.registerUser(file, newUser);
				cpfToAuth = newUser.getCpf();
			}
		}
		while (!keepAlive.equals("n")) {
			System.out.println("\n----------Welcome!----------\n");
			// Managing Events
			System.out.println("\n----------App Options---------");
			System.out.println("\nType 1 to manage the available events");
			System.out.println("\nType 2 to create a new event");
			System.out.println("\nType n to skip\n");
			String option = scan.nextLine();
			
			while(!option.equals("n")) {
				if(option.equals("1")) {
					System.out.println("\n-------!!!Past Events!!!-------\n");
					
					// Prints not available events
					for(Event event : file.getPastEventsList()) {
						System.out.println("Event - "+event.toString());
					}
					
					System.out.println("\n-------!!!Events Happening!!!-------\n");
					
					// Prints events happening
					for(Event event : file.getEventsHappening()) {
						System.out.println("Event - "+event.toString());
					}
					
					System.out.println("\n----------Available Events----------\n");
					
					// Prints available events with a identification number
					for(Event event : file.getEventsToHappenList()) {
						System.out.println("Event - "+event.toString());
					}
					
					System.out.println("\n----------Event Management---------");
					System.out.println("\nType 1 to confirm presence at an event");
					System.out.println("\nType 2 to cancel presence at an event");
					System.out.println("\nType n to skip\n");
					String operation = scan.nextLine();
					while(!operation.equals("n")) {
						User userToRegisterPresence = FileModelController.findUserByCPF(file, cpfToAuth);
						if (operation.equals("1")) {
							// Confirming presence on an event
							System.out.println("\nType the ID of the event to confirm presence: ");
							Integer id = 0;
							try {
								// Needs to pass an integer
								id = Integer.parseInt(scan.nextLine());
							} catch(Exception e) {
								e.printStackTrace();
								// One more try
								System.out.println("\nType the ID of the event to confirm presence: ");
								id = Integer.parseInt(scan.nextLine());
							}
							// Avoid throwing not final variable in lambda expression exception
							Integer eventId = id;
							
							Event eventToHappen = file.getEventsToHappenList().stream()
									.filter(event -> event.getId() == eventId)
									.findAny().orElse(null);
							Event eventHappening = file.getEventsHappening().stream()
									.filter(event -> event.getId() == eventId)
									.findAny().orElse(eventToHappen);
							
							
							// If the event ID exists, register the presence
							if(!eventHappening.equals(null)) {
								FileModelController.registerPresence(file, eventId, userToRegisterPresence);
								
								System.out.println("\nContinue confirming presence? (Y/n): ");
								operation = scan.nextLine();
								if (!operation.equals("n")) operation = "1";
								else {
									System.out.println("\nDo you want to cancel presence on an event? (Y/n): ");
									operation = scan.nextLine();
									if (!operation.equals("n")) operation = "2";
								}
							}
							else{
								System.out.println("\n----------No available event number found!----------\n");
								System.out.println("\nContinue confirming presence? (Y/n): ");
								operation = scan.nextLine();
								if (!operation.equals("n")) operation = "1";
								else {
									System.out.println("\nDo you want to cancel presence at an event? (Y/n): ");
									operation = scan.nextLine();
									if (!operation.equals("n")) operation = "2";
								}
							}
						} else if (operation.equals("2")) {
							// Canceling presence on an event
							System.out.println("\nType the ID of the event to cancel presence: ");
							Integer id = 0;
							try {
								// Needs to pass an integer
								id = Integer.parseInt(scan.nextLine());
							} catch(Exception e) {
								e.printStackTrace();
								// One more try
								System.out.println("\nType the number of the event to cancel presence: ");
								id = Integer.parseInt(scan.nextLine());
							}
							// Avoid Exception for not final variable in lambda expression
							Integer eventId = id;
							
							Event eventToHappen = file.getEventsToHappenList().stream()
									.filter(event -> event.getId() == eventId)
									.findAny().orElse(null);
							Event eventHappening = file.getEventsHappening().stream()
									.filter(event -> event.getId() == eventId)
									.findAny().orElse(eventToHappen);
							
							// If the event id exists, cancel the presence
							if(!eventHappening.equals(null)) {
								FileModelController.cancelPresence(file, eventId, userToRegisterPresence);
								
								System.out.println("\nContinue canceling presence? (Y/n): ");
								operation = scan.nextLine();
								if (!operation.equals("n")) operation = "2";
								else {
									System.out.println("\nDo you want to confirm presence at an event? (Y/n): ");
									operation = scan.nextLine();
									if (!operation.equals("n")) operation = "1";
								}
							}
							else{
								System.out.println("\n----------No available event number found!----------\n");
								System.out.println("\nContinue confirming presence? (Y/n): ");
								operation = scan.nextLine();
								if (!operation.equals("n")) operation = "1";
								else {
									System.out.println("\nDo you want to cancel presence on an event? (Y/n): ");
									operation = scan.nextLine();
									if (!operation.equals("n")) operation = "2";
								}
							}
						}
						else {
							System.out.println("\nType 1 to confirm presence at an event");
							System.out.println("\nType 2 to cancel presence at an event");
							System.out.println("\nType n to skip\n");
							operation = scan.nextLine();
						}
					}
					System.out.println("\n----------App Options---------");
					System.out.println("\nType 1 to manage the available events");
					System.out.println("\nType 2 to create a new event");
					System.out.println("\nType n to skip\n");
					option = scan.nextLine();
				} else if(option.equals("2")) {
					while(option.equals("2")) {
						// Create a new event
						Event newEvent = EventController.signEvent(scan);
						
						// Register event on the file model
						FileModelController.registerEvent(file, newEvent);
						
						System.out.println("\n----------Event Created----------");
						
						System.out.println("\n----------App Options---------");
						System.out.println("\nType 1 to manage the available events");
						System.out.println("\nType 2 to create a new event");
						System.out.println("\nType n to skip\n");
						option = scan.nextLine();
					}
					
				} else {
					System.out.println("\n----------App Options---------");
					System.out.println("\nType 1 to manage the available events");
					System.out.println("\nType 2 to create a new event");
					System.out.println("\nType n to skip\n");
					option = scan.nextLine();
				}
			}
			
			System.out.println("\n----------End----------");
			
			System.out.println("\nDo you want to go back to the start? (Y/n): ");
			keepAlive = scan.nextLine();
			if(!keepAlive.equals("n")) {
				System.out.println("\n----------Restarting Program----------\n");
			}
		}
		
		System.out.println("\n----------Program Finished----------");
		scan.close();
		
		// Write the file with the model changes
		FileModelController.writeFile("./src/com/resources/events.data", file);
	}

}
