package com.java.cityEvents.model;

import java.util.List;
import java.util.Objects;

public class User {
	
	private String name;
	private String cpf;
	private Integer age;
	private String city;
	private List<Integer> confirmedEvents;
	
	public User() {}

	public User(String name, String cpf, Integer age, String city, List<Integer> confirmedEvents) {
		this.name = name;
		this.cpf = cpf;
		this.age = age;
		this.city = city;
		this.confirmedEvents = confirmedEvents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Integer> getConfirmedEvents() {
		return confirmedEvents;
	}

	public void setConfirmedEvents(List<Integer> confirmedEvents) {
		this.confirmedEvents = confirmedEvents;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, city, cpf, confirmedEvents, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(age, other.age) && Objects.equals(city, other.city) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(confirmedEvents, other.confirmedEvents) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", cpf=" + cpf + ", age=" + age + ", city=" + city + ", eventsConfirmed="
				+ confirmedEvents + "]";
	}
	
}
