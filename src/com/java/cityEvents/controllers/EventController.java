package com.java.cityEvents.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.java.cityEvents.model.Event;

public class EventController {
	
	public static Event signEvent(Scanner scan) {
		// Asks user for every information from the event
		System.out.println("\n----------Signing Event----------\n");
		
		System.out.println("Type event name: ");
		String eventName = scan.nextLine();
		
		System.out.println("\nType event address: ");
		String address = scan.nextLine();
		
		// Defines the categories accepted
		String[] categoryList = {"Festa", "Evento Esportivo", "Evento Beneficente", "Show Musical", "Teatro"};
		
		// Prints the options of categories on the screen
		System.out.println("\nType event category number: ");
		for (int i = 1; i<=categoryList.length; ++i) {
			System.out.println(" "+i+" - "+categoryList[i-1]+"\n");
		}
		
		// User type the number of the category chosen
		String category = categoryList[Integer.parseInt(scan.nextLine())-1];
		
		// Receives date as a string in the same format as the other dates
		StringBuffer sb = new StringBuffer();
		
		System.out.println("\nType the event day: ");
		String day = scan.nextLine();
		if(day.length()==1) day = "0"+day;
		sb.append(day).append("/");
		
		System.out.println("\nType the event month: ");
		String month = scan.nextLine();
		if(month.length()==1) month = "0"+month;
		sb.append(month).append("/");
		
		System.out.println("\nType the event year: ");
		sb.append(scan.nextLine()).append(" ");
		
		System.out.println("\nType the event hour: ");
		String hour = scan.nextLine();
		if(hour.length()==1) hour = "0"+hour;
		sb.append(hour).append(":");
		
		System.out.println("\nType the event minutes: ");
		String minutes = scan.nextLine();
		if(minutes.length()==1) minutes = "0"+minutes;
		sb.append(minutes);
		
		// Creates date object
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date eventDate = null;
		try {
			eventDate = df.parse(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nType a single-line description for the event: ");
		String description = scan.nextLine();
		
		// Empty set of CPFs because it's a new event
		// User must confirm presence
		Set<String> confirmedCPFs = new HashSet<>();
		
		// Returns the new event
		return new Event(eventName, address, category, eventDate, description, confirmedCPFs );
	}
}
