package app.netlify.dsubha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.dsubha.dao.PGAdminRepository;
import app.netlify.dsubha.dao.PGRepository;
import app.netlify.dsubha.dao.UserRepository;
import app.netlify.dsubha.entity.PG;
import app.netlify.dsubha.entity.PGAdmin;
import app.netlify.dsubha.entity.User;

@Component
public class PGAdminService {
	@Autowired
	PGAdminRepository pgAdminRepository;

	@Autowired
	PGRepository pgRepository;

	@Autowired
	UserRepository userRepository;

	public PGAdmin assignPGAdmin(String userId, String pgId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		PG pg = pgRepository.findById(pgId).orElseThrow(() -> new RuntimeException("PG not found"));

		PGAdmin admin = new PGAdmin(pg, user);
		return pgAdminRepository.save(admin);
	}

}
