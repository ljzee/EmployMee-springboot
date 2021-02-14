package com.employmee.employmee.payload.response;

public class DashboardJobPost {
	
	private int id;
	
	private String title;
	
	private String city;
	
	private String state;

	public DashboardJobPost(int id, String title, String city, String state) {
		this.id = id;
		this.title = title;
		this.city = city;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
