package app.netlify.dsubha.dto;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.dsubha.dao.PGRepository;
import app.netlify.dsubha.entity.PG;

@Component
public class PGService {
	@Autowired
	PGRepository pgRepository;

	public PG createNewPg(PG pg) throws MalformedURLException {
		return pgRepository.save(pg);
	}

	public PG getPGByID(String id) {
		Optional<PG> pgOptional = pgRepository.findById(id);
		return pgOptional.get();
	}

	public List<PG> getAllPGs() {
		return (List<PG>) pgRepository.findAll();
	}

	public PG updatePGDetails(PG pg) throws MalformedURLException {
		PG fetchedPg = this.getPGByID(pg.getPgId());
		if (pg.getPgName() != null) {
			fetchedPg.setPgName(pg.getPgName());
		}
		if (pg.getAddress() != null) {
			fetchedPg.setAddress(pg.getAddress());
		}
		if (pg.getWebsite() != null) {
			fetchedPg.setWebsite(pg.getWebsite().toString());
		}
		if (pg.getCountryCode() != null) {
			fetchedPg.setCountryCode(pg.getCountryCode());
		}
		if (pg.getTimezone() != null) {
			fetchedPg.setTimezone(pg.getTimezone().toString());
		}
		if (pg.getCurrency() != null) {
			fetchedPg.setCurrency(pg.getCurrency().getCurrencyCode());
		}
		return pgRepository.save(fetchedPg);
	}
}
