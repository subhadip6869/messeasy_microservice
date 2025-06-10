package app.netlify.dsubha.entity;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Column(name = "photo_url", nullable = true)
	private URL photoUrl;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPG> userPGs;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PGAdmin> admins;

	@Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime created;

	@PrePersist
	protected void onCreate() {
		this.created = LocalDateTime.now();
	}

	public User() {
	}

	public User(String userId, String name, String email, String contactNo, String photoUrl)
			throws MalformedURLException {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.contactNo = contactNo;
		this.photoUrl = new URL(photoUrl);
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

	public URL getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) throws MalformedURLException {
		this.photoUrl = new URL(photoUrl);
	}

	public List<UserPG> getUserPGs() {
		return userPGs;
	}

	public void setUserPGs(List<UserPG> userPGs) {
		this.userPGs = userPGs;
	}

	public List<PGAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<PGAdmin> admins) {
		this.admins = admins;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", email=" + email + ", contactNo=" + contactNo
				+ ", created=" + created + "]";
	}
}
