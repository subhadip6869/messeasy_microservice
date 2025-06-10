package app.netlify.dsubha.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.dsubha.dao.UserRepository;
import app.netlify.dsubha.entity.User;

@Component
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User createNewUser(User user) {
		return userRepository.save(user);
	}

	public User getUserByID(String userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.get();
	}

	public User getUserByEmail(String email) {
		List<User> users = userRepository.findByEmail(email);
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User updateUserDetails(User user) throws MalformedURLException {
		User fetchedUser = this.getUserByID(user.getUserId());
		if (user.getName() != null) {
			fetchedUser.setName(user.getName());
		}
		if (user.getEmail() != null) {
			fetchedUser.setEmail(user.getEmail());
		}
		if (user.getContactNo() != null) {
			fetchedUser.setContactNo(user.getContactNo());
		}
		if (user.getPhotoUrl() != null) {
			fetchedUser.setPhotoUrl(user.getPhotoUrl().toString());
		}
		return userRepository.save(fetchedUser);
	}

	public User deleteUserByID(String userId) {
		try {
			User user = this.getUserByID(userId);
			userRepository.delete(user);
			return user;
		} catch (Exception e) {
			return null;
		}

	}
}
