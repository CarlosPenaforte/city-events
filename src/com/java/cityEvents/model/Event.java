package com.java.cityEvents.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Event implements Comparable<Event>{
	
	private String name;
	private String address;
	private String category;
	// Preferred to use Date object to be able to compare two dates easily
	private Date time;
	private String description;
	// Preferred to use Set to avoid registering the same CPF
	private Set<String> confirmedCPFs;
	private Integer id;
	
	public Event() {}

	public Event(String name, String address, String category, Date time, String description, Set<String> confirmedCPFs) {
		this.name = name;
		this.address = address;
		this.category = category;
		this.time = time;
		this.description = description;
		this.confirmedCPFs = confirmedCPFs;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Set<String> getConfirmedCPFs() {
		return confirmedCPFs;
	}
	
	public void setConfirmedCPFs(Set<String> confirmedCPFs) {
		this.confirmedCPFs = confirmedCPFs;
	}

	@Override
	public String toString() {
		// Formats date to improve accessibility
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "ID: "+ id +", name: " + name + ", address: " + address + ", category: " + category + ", time: " + df.format(time)
				+ ", description: " + description + ", confirmedCPFs: " + confirmedCPFs.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, category, description, name, time, id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(address, other.address) && category == other.category
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(time, other.time) && Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(Event o) {
		if(this.getTime().before(o.getTime())) return -1;
		if(this.getTime().equals(o.getTime())) return 0;
		return 1;
	}
}
