package com.employmee.employmee.payload.response;

import com.employmee.employmee.entity.Document;

public class UserDocument {
	private int id;
	
	private String fileName;
	
	private String type;
	
	private long size;
	
	private String dateUploaded;
	
	public UserDocument() {}

	public UserDocument(Document document) {
		this.id = document.getId();
		this.fileName = document.getFileName();
		this.type = document.getType().name();
		this.size = document.getSize();
		this.dateUploaded = document.getDateUploaded().toString();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	
	
}
