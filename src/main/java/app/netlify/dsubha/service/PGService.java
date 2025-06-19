package app.netlify.dsubha.service;

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

	public PG updatePGDetails(String pgId, PG pg) throws MalformedURLException {
		PG fetchedPg = this.getPGByID(pgId);
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

	public PG deletePGById(String pgId) {
		try {
			PG pg = this.getPGByID(pgId);
			pgRepository.delete(pg);
			return pg;
		} catch (Exception e) {
			return null;
		}
	}

}
