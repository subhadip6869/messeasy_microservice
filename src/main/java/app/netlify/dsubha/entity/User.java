package app.netlify.dsubha.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "name", length = 80, nullable = false)
	private String name;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "contactNo", length = 20, nullable = true)
	private String contactNo;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pg_id", referencedColumnName = "pg_id")
	private PG pg;

	@Column(name = "joining_date", nullable = true)
	private LocalDate joiningDate;

	@Column(name = "leaving_date", nullable = true)
	private LocalDate leavingDate;

	@Column(name = "created")
	private LocalDateTime created;

	@PrePersist
	protected void onCreate() {
		this.created = LocalDateTime.now();
	}

	public User() {
	}

	public User(String userId, String name, String email, String contactNo, LocalDate joiningDate,
			LocalDate leavingDate) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.joiningDate = joiningDate;
		this.leavingDate = leavingDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public PG getPg() {
		return pg;
	}

	public void setPg(PG pg) {
		this.pg = pg;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public LocalDate getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(LocalDate leavingDate) {
		this.leavingDate = leavingDate;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", email=" + email + ", contactNo=" + contactNo + ", pg="
				+ pg + ", joiningDate=" + joiningDate + ", leavingDate=" + leavingDate + ", created=" + created + "]";
	}
}
