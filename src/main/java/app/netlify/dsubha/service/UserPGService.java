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

	public UserPG assignUserToPg(String userId, String pgId, String adminId, boolean userJoined) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		PG pg = pgRepository.findById(pgId).orElseThrow(() -> new RuntimeException("PG not found"));

		if (!userJoined && !pg.getAdmin().get("userId").equals(adminId)) {
			throw new RuntimeException("User is not authorized to add members");
		}

		UserPG userPG = new UserPG(user, pg, userJoined);
		return userPGRepository.save(userPG);
	}

	public UserPG removeUserFromPG(String userId, String pgID, String adminId) {
		PG pg = pgRepository.findById(pgID).orElse(null);
		UserPG userPG = userPGRepository.findByPgAndUser(pgID, userId);
		if (userPG != null) {
			if (!pg.getAdmin().get("userId").equals(adminId)) {
				throw new RuntimeException("User is not authorized to remove members");
			}
			userPGRepository.delete(userPG);
		}
		return userPG;
	}

	public UserPG joinUserPG(String userId, String pgId) {
		UserPG userPG = userPGRepository.findByPgAndUser(pgId, userId);
		if (userPG != null) {
			userPG.setUserJoined(true);
			userPGRepository.save(userPG);
		}
		return userPG;
	}
}
