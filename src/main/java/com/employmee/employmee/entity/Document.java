package com.employmee.employmee.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.io.FilenameUtils;

@Entity
@Table(name = "documents")
public class Document {
	
	public enum TYPE {
		RESUME,
		COVERLETTER,
		OTHER
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private TYPE type;
	
	@Column(name = "size")
	private long size;
	
	@Column(name = "date_published")
	private LocalDate dateUploaded;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserProfile userProfile;
	
	public Document() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public LocalDate getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(LocalDate dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getFileName() {
		return this.getName() + "." + FilenameUtils.getExtension(this.getPath());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
}
