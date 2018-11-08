package com.qa.cv.Model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class UsersDataModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String email;

	@NotBlank
	private String password;
	
	@Lob
	private SerialBlob picture;
	
	private String prefLocation;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "departmentId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private DepartmentModel departmentId;

	public UsersDataModel() {

	}

	public UsersDataModel(String firstName, String lastName, String email, String password,
			DepartmentModel department, SerialBlob picture, String prefLocation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.picture = picture;
		this.prefLocation = prefLocation;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DepartmentModel getDepartment() {
		return departmentId;
	}

	public void setDepartment(DepartmentModel department) {
		this.departmentId = department;
	}
	public Long getDepartmentId() 
	{
		return departmentId.getDepartmentId();
	}

	public SerialBlob getPicture() {
		return picture;
	}

	public void setPicture(SerialBlob picture) {
		this.picture = picture;
	}

	public String getPrefLocation() {
		return prefLocation;
	}

	public void setPrefLocation(String prefLocation) {
		this.prefLocation = prefLocation;
	}

	
}
