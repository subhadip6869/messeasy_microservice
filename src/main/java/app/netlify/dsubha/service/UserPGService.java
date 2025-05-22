package app.netlify.dsubha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.dsubha.dao.PGRepository;
import app.netlify.dsubha.dao.UserPGRepository;
import app.netlify.dsubha.dao.UserRepository;
import app.netlify.dsubha.entity.PG;
import app.netlify.dsubha.entity.User;
import app.netlify.dsubha.entity.UserPG;

@Component
public class UserPGService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PGRepository pgRepository;

	@Autowired
	UserPGRepository userPGRepository;

	public UserPG assignUserToPg(String userId, String pgId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		PG pg = pgRepository.findById(pgId).orElseThrow(() -> new RuntimeException("PG not found"));

		UserPG userPG = new UserPG(user, pg);
		return userPGRepository.save(userPG);
	}

}
