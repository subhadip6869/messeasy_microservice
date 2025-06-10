package app.netlify.dsubha.entity;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Currency;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pgs")
public class PG {
	@Id
	@Column(name = "pg_id")
	private String pgId;

	@Column(name = "pg_name", length = 100, nullable = false)
	private String pgName;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "website", nullable = true)
	private URL website;

	@Column(name = "country_code", length = 2, nullable = false)
	private String countryCode;

	@Column(name = "timezone", length = 50, nullable = false)
	private ZoneId timezone;

	@Column(name = "currency", length = 3, nullable = false)
	private Currency currency;

	@Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime created;

	@JsonManagedReference
	@OneToMany(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPG> userPGs;

	@JsonManagedReference
	@OneToOne(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true)
	private PGAdmin admin;

	@PrePersist
	protected void onCreate() {
		this.pgId = UUID.randomUUID().toString();
		this.created = LocalDateTime.now();
	}

	public PG() {
	}

	public PG(String pgName, String address, String website, String countryCode, String timezone, String currency)
			throws MalformedURLException {
		this.pgName = pgName;
		this.address = address;
		this.website = new URL(website);
		this.countryCode = countryCode;
		this.timezone = ZoneId.of(timezone);
		this.currency = Currency.getInstance(currency);
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public URL getWebsite() {
		return website;
	}

	public void setWebsite(String website) throws MalformedURLException {
		this.website = new URL(website);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) throws IllformedLocaleException {
		String[] countryCodes = Locale.getISOCountries();
		if (Arrays.asList(countryCodes).contains(countryCode)) {
			this.countryCode = countryCode;
		} else {
			throw new IllformedLocaleException("Invalid country code");
		}
	}

	public ZoneId getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		ZoneId zoneId = ZoneId.of(timezone);
		this.timezone = zoneId;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		Currency curr = Currency.getInstance(currency);
		this.currency = curr;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getPgId() {
		return pgId;
	}

	public List<UserPG> getUserPGs() {
		return userPGs;
	}

	public void setUserPGs(List<UserPG> userPGs) {
		this.userPGs = userPGs;
	}

	public PGAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(PGAdmin admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "PGs [pgId=" + pgId + ", pgName=" + pgName + ", address=" + address + ", website=" + website
				+ ", countryCode=" + countryCode + ", timezone=" + timezone + ", currency=" + currency + ", created="
				+ created + ", admin=" + admin.getUser().getUserId() + "]";
	}

}
