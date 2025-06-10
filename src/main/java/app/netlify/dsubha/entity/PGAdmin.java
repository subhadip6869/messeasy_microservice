package app.netlify.dsubha.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pg_admin")
public class PGAdmin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "pg_id", unique = true, nullable = false)
	private PG pg;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public PGAdmin() {
	}

	public PGAdmin(PG pg, User user) {
		this.pg = pg;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public PG getPg() {
		return pg;
	}

	public void setPg(PG pg) {
		this.pg = pg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PGAdmin [id=" + id + ", pg=" + pg + ", user=" + user + "]";
	}
}
