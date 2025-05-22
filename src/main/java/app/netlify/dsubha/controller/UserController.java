package app.netlify.dsubha.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.dsubha.entity.User;
import app.netlify.dsubha.entity.UserPG;
import app.netlify.dsubha.repository.ResponseRepository;
import app.netlify.dsubha.service.UserPGService;
import app.netlify.dsubha.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserPGService userPGService;

	@PostMapping("/")
	public ResponseEntity<ResponseRepository<User>> createUser(@RequestBody User user) {
		try {
			User newUser = userService.createNewUser(user);
			return ResponseEntity.ok(new ResponseRepository<User>(HttpStatus.OK, "Success", newUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseRepository<User>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseRepository<User>> getUserById(@PathVariable String id) {
		try {
			User fetchedUser = userService.getUserByID(id);
			return ResponseEntity.ok(new ResponseRepository<User>(HttpStatus.OK, "Success", fetchedUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseRepository<User>(HttpStatus.NOT_FOUND, e.getMessage(), null));
		}
	}

	@PutMapping("/")
	public ResponseEntity<ResponseRepository<User>> updateUserData(@RequestBody User user) {
		try {
			User updatedUser = userService.updateUserDetails(user);
			return ResponseEntity.ok(new ResponseRepository<User>(HttpStatus.OK, "Success", updatedUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseRepository<User>(HttpStatus.NOT_FOUND, e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseRepository<User>> deleteUser(@PathVariable String id) {
		User delUser = userService.deleteUserByID(id);
		if (delUser != null) {
			return ResponseEntity.ok(new ResponseRepository<User>(HttpStatus.OK, "Success", delUser));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseRepository<User>(HttpStatus.NOT_FOUND, "User doesn't exist", null));
		}
	}

	@PostMapping("/assign-pg")
	public ResponseEntity<ResponseRepository<UserPG>> assignUserToPG(@RequestBody Map<String, String> request) {
		try {
			UserPG userPG = userPGService.assignUserToPg(request.get("userId"), request.get("pgId"));
			return ResponseEntity.ok(new ResponseRepository<>(HttpStatus.OK, "PG assigned to user", userPG));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseRepository<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

}
