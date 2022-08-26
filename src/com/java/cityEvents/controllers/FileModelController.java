package com.java.cityEvents.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.java.cityEvents.model.Event;
import com.java.cityEvents.model.FileModel;
import com.java.cityEvents.model.User;

public class FileModelController {

	public static FileModel readFile(String filePath) {
		// Create or open a file
		File file = new File(filePath);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		// Read a file lines and add in a list
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		List<String> lineList = new ArrayList<>();
		
		try {
			String line = br.readLine();
			
			// Reads not null lines and add them in the list
			while(line != null) {
				lineList.add(line);
				line = br.readLine();
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			// Close readers
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fr.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		// Initialize sets and lists to create file model
		Set<User> userList = new HashSet<>();
		Set<String> registeredCPFs = new HashSet<>();
		List<Event> eventToHappenList = new ArrayList<>();
		List<Event> eventsHappening = new ArrayList<>();
		List<Event> pastEventList = new ArrayList<>();
		Integer newId = 1;
		
		// Create a simple date format for general users understand
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for (int i = 0; i<lineList.size() ;++i) {
			
			// Register a new user if a newUser line is found
			if (lineList.get(i).equals("newUser")) {
				User user = new User();
				user.setName(lineList.get(i+1));
				user.setCpf(lineList.get(i+2));
				user.setAge(Integer.parseInt(lineList.get(i+3)));
				user.setCity(lineList.get(i+4));
				
				// Reads confirmed events by this user
				Integer size = lineList.get(i+5).length();
				List<Integer> confirmedEvents = new ArrayList<>();
				
				if(lineList.get(i+5).contains(",")) {
					// Removes '[', ']' and ', ' to have a set with only the id's of the events as strings
					List<String> stringEventsId = Arrays.asList(lineList.get(i+5).substring(1,size-1).split(", "));
					// Fills the confirmed events id's list with the integer values
					for (String stringId : stringEventsId) confirmedEvents.add(Integer.parseInt(stringId));
					
					user.setConfirmedEvents(confirmedEvents);
				}
				else if (lineList.get(i+5).length() > 2) {
					// This is the case of only one event id confirmed
					// Remove only '[' and ']'
					String stringEventId = lineList.get(i+5).substring(1,size-1);
					confirmedEvents.add(Integer.parseInt(stringEventId));
					user.setConfirmedEvents(confirmedEvents);
				}
				else
					// No event id confirmed, so create an empty list
					user.setConfirmedEvents(confirmedEvents);
				
				
				// Adds user to the list of users and CPFs to be added on file model
				userList.add(user);
				registeredCPFs.add(user.getCpf());
			}
			
			// Register a new Event if a newEvent line is found
			else if (lineList.get(i).equals("newEvent")) {
				Event event = new Event();
				event.setName(lineList.get(i+1));
				event.setAddress(lineList.get(i+2));
				event.setCategory(lineList.get(i+3));
				
				// Parse time on the file to Date object
				try {
					event.setTime(df.parse(lineList.get(i+4)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				event.setDescription(lineList.get(i+5));
				
				Integer size = lineList.get(i+6).length();
				
				// Create a Set of CPFs reading a string with the format of an array
				if(lineList.get(i+6).contains(","))
					// Removes '[', ']' and ', ' to have a set with only the CPFs
					event.setConfirmedCPFs(new HashSet<String>(Arrays.asList(lineList.get(i+6).substring(1,size-1).split(", "))));
				else if (lineList.get(i+6).length() > 2)
					// This is the case of only one CPF confirmed
					// Remove only '[' and ']'
					event.setConfirmedCPFs(new HashSet<String>(Arrays.asList(lineList.get(i+6).substring(1,size-1))));
				else {
					// No CPF confirmed, so create a new set
					event.setConfirmedCPFs(new HashSet<String>());
				}
				
				event.setId(Integer.parseInt(lineList.get(i+7)));
				
				// Adds event to a list, depending if the time of the event is before of the present time
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				try {
					Date date = df.parse(dtf.format(LocalDateTime.now()));
					
					// Events on the past
					if(event.getTime().before(date)) {
						if(Math.abs(date.getTime()-event.getTime().getTime())<3600000) eventsHappening.add(event);
						else pastEventList.add(event);
					}
					// Events to happen
					else eventToHappenList.add(event);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (lineList.get(i).equals("newId")) {
				newId = Integer.parseInt(lineList.get(i+1));
			}
		}
		
		// Sort events by date
		eventToHappenList.sort(Comparator.naturalOrder());
		eventsHappening.sort(Comparator.naturalOrder());
		pastEventList.sort(Comparator.naturalOrder());
		
		// returns the model
		return new FileModel(userList, registeredCPFs, eventToHappenList, eventsHappening, pastEventList, newId);
	}
	
	public static void registerUser(FileModel file, User newUser) {
		// Adds a new user in the file model
		file.getUserList().add(newUser);
		
		// Register the user CPF
		file.getRegisteredCPFs().add(newUser.getCpf());
	}
	
	public static void registerEvent(FileModel file, Event newEvent) {
		
		newEvent.setId(file.getNewId());
		file.setNewId(file.getNewId()+1);
		
		// Creates data formats
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		try {
			// Check the current date
			Date date = df.parse(dtf.format(LocalDateTime.now()));
			
			// If the new event is in the past, adds to past events list
			if(newEvent.getTime().before(date)) {
				if(Math.abs(date.getTime()-newEvent.getTime().getTime())<3600000) file.getEventsHappening().add(newEvent);
				else file.getPastEventsList().add(newEvent);
			}
			
			// else, adds to events to happen list
			else file.getEventsToHappenList().add(newEvent);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Sort by date again
		file.getEventsToHappenList().sort(Comparator.naturalOrder());
		file.getEventsHappening().sort(Comparator.naturalOrder());
		file.getPastEventsList().sort(Comparator.naturalOrder());
	}
	
	public static User findUserByCPF(FileModel file, String cpf) {
		User foundUser = file.getUserList().stream()
				.filter(user -> cpf.equals(user.getCpf()))
				.findAny().orElse(null);
		return foundUser;
	}
	
	public static void registerPresence(FileModel file, int eventId, User user) {
		
		Event eventToHappen = file.getEventsToHappenList().stream()
				.filter(event -> event.getId() == eventId)
				.findAny().orElse(null);
		Event eventHappening = file.getEventsHappening().stream()
				.filter(event -> event.getId() == eventId)
				.findAny().orElse(eventToHappen);
		// Checks if the CPF is already confirmed on the event
		if (eventHappening.getConfirmedCPFs().contains(user.getCpf()))
			System.out.println("\n!!!PRESENCE ALREADY CONFIRMED!!!");
		
		// Adds event id to the user list of events confirmed and adds CPF on the set of confirmed CPFs
		else {
			file.getUserList().remove(user);
			user.getConfirmedEvents().add(eventId);
			file.getUserList().add(user);
			eventHappening.getConfirmedCPFs().add(user.getCpf());
			System.out.println("\n----------Presence Confirmed----------\n");
		}
	}
	
	public static void cancelPresence(FileModel file, int eventId, User user) {
		Event eventToHappen = file.getEventsToHappenList().stream()
				.filter(event -> event.getId() == eventId)
				.findAny().orElse(null);
		Event eventHappening = file.getEventsHappening().stream()
				.filter(event -> event.getId() == eventId)
				.findAny().orElse(eventToHappen);
		// Removes CPF from the confirmed set and remove event id from user confirmed events
		eventHappening.getConfirmedCPFs().remove(user.getCpf());
		file.getUserList().remove(user);
		user.getConfirmedEvents().remove(Integer.valueOf(eventId));
		file.getUserList().add(user);
		System.out.println("\n----------Presence Canceled----------\n");
	}
	
	public static void writeFile(String filePath, FileModel fileModel) {
		// Search file and define the date format
		File file = new File(filePath);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		// Cleaning File
		try {
			new FileWriter(filePath, false).close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Writing in the file
		PrintStream fileStream = null;
		try {
			fileStream = new PrintStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Writing users informations
		for (User user : fileModel.getUserList()) {
			fileStream.println("newUser");
			fileStream.println(user.getName());
			fileStream.println(user.getCpf());
			fileStream.println(user.getAge());
			fileStream.println(user.getCity());
			fileStream.println(user.getConfirmedEvents());
		}
		
		// Writing events to happen informations
		for (Event event : fileModel.getEventsToHappenList()) {
			fileStream.println("newEvent");
			fileStream.println(event.getName());
			fileStream.println(event.getAddress());
			fileStream.println(event.getCategory());
			// Formats date to increase accessibility
			fileStream.println(df.format(event.getTime()));
			fileStream.println(event.getDescription());
			fileStream.println(event.getConfirmedCPFs());
			fileStream.println(event.getId());
		}
		
		// Writing events happening
		for (Event event : fileModel.getEventsHappening()) {
			fileStream.println("newEvent");
			fileStream.println(event.getName());
			fileStream.println(event.getAddress());
			fileStream.println(event.getCategory());
			// Formats date to increase accessibility
			fileStream.println(df.format(event.getTime()));
			fileStream.println(event.getDescription());
			fileStream.println(event.getConfirmedCPFs());
			fileStream.println(event.getId());
		}
		
		// Writing past events informations
		for (Event event : fileModel.getPastEventsList()) {
			fileStream.println("newEvent");
			fileStream.println(event.getName());
			fileStream.println(event.getAddress());
			fileStream.println(event.getCategory());
			// Formats date to increase accessibility
			fileStream.println(df.format(event.getTime()));
			fileStream.println(event.getDescription());
			fileStream.println(event.getConfirmedCPFs());
			fileStream.println(event.getId());
		}
		
		fileStream.println("newId");
		fileStream.println(fileModel.getNewId());
		
		// Closing writer
		fileStream.close();
		
	}
}
