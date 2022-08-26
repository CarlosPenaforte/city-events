package com.java.cityEvents.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FileModel {
	// Sets for avoiding repetition
	private Set<User> userList;
	private Set<String> registeredCPFs;
	// Lists for working with its indexes
	private List<Event> eventsToHappenList;
	private List<Event> eventsHappening;
	private List<Event> pastEventsList;
	private Integer newId = 1;
	
	public FileModel(Set<User> userList, Set<String> registeredCPFs, List<Event> eventsToHappenList,
			List<Event> eventsHappening, List<Event> pastEventsList, int newId) {
		this.userList = userList;
		this.registeredCPFs = registeredCPFs;
		this.eventsToHappenList = eventsToHappenList;
		this.eventsHappening = eventsHappening;
		this.pastEventsList = pastEventsList;
		this.newId = newId;
	}

	public FileModel() {}
	
	public Integer getNewId() {
		return newId;
	}

	public void setNewId(Integer newId) {
		this.newId = newId;
	}

	public List<Event> getEventsHappening() {
		return eventsHappening;
	}

	public void setEventsHappening(List<Event> eventsHappening) {
		this.eventsHappening = eventsHappening;
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

	public Set<String> getRegisteredCPFs() {
		return registeredCPFs;
	}

	public void setRegisteredCPFs(Set<String> registeredCPFs) {
		this.registeredCPFs = registeredCPFs;
	}

	public List<Event> getEventsToHappenList() {
		return eventsToHappenList;
	}

	public void setEventsToHappenList(List<Event> eventsToHappenList) {
		this.eventsToHappenList = eventsToHappenList;
	}

	public List<Event> getPastEventsList() {
		return pastEventsList;
	}

	public void setPastEventsList(List<Event> pastEventsList) {
		this.pastEventsList = pastEventsList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventsHappening, eventsToHappenList, pastEventsList, registeredCPFs, userList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileModel other = (FileModel) obj;
		return Objects.equals(eventsHappening, other.eventsHappening)
				&& Objects.equals(eventsToHappenList, other.eventsToHappenList)
				&& Objects.equals(pastEventsList, other.pastEventsList)
				&& Objects.equals(registeredCPFs, other.registeredCPFs) && Objects.equals(userList, other.userList);
	}

	@Override
	public String toString() {
		return "FileModel [userList=" + userList + ", registeredCPFs=" + registeredCPFs + ", eventsToHappenList="
				+ eventsToHappenList + ", eventsHappening=" + eventsHappening + ", pastEventsList=" + pastEventsList
				+ "]";
	}
	
}
