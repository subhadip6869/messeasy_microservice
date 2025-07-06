package app.netlify.dsubha.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import app.netlify.dsubha.entity.embeddable.UserPGId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_pg")
public class UserPG {

	@EmbeddedId
	private UserPGId id = new UserPGId();

	@JsonBackReference
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@JsonBackReference
	@ManyToOne
	@MapsId("pgId")
	@JoinColumn(name = "pg_id")
	private PG pg;

	@Column(name = "joining_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime joiningDate;

	@Column(name = "user_joined")
	private boolean userJoined;

	@PrePersist
	protected void onCreate() {
		this.joiningDate = LocalDateTime.now();
	}

	public UserPG() {
	}

	public UserPG(User user, PG pg, LocalDateTime joiningDate, boolean userJoined) {
		this.user = user;
		this.pg = pg;
		this.joiningDate = joiningDate;
		this.userJoined = userJoined;
	}

	public UserPG(User user, PG pg, boolean userJoined) {
		this.user = user;
		this.pg = pg;
		this.userJoined = userJoined;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PG getPg() {
		return pg;
	}

	public void setPg(PG pg) {
		this.pg = pg;
	}

	public LocalDateTime getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDateTime joiningDate) {
		this.joiningDate = joiningDate;
	}

	public boolean isUserJoined() {
		return userJoined;
	}

	public void setUserJoined(boolean userJoined) {
		this.userJoined = userJoined;
	}
}
