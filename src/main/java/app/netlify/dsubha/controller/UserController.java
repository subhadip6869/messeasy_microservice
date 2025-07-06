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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.dsubha.entity.User;
import app.netlify.dsubha.entity.UserPG;
import app.netlify.dsubha.helpers.ResponseHelper;
import app.netlify.dsubha.service.UserPGService;
import app.netlify.dsubha.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserPGService userPGService;

	@PostMapping
	public ResponseEntity<ResponseHelper<User>> createUser(@RequestBody Map<String, String> requestBody) {
		try {
			User newUser = userService.createNewUser(new User(requestBody.get("userId"), requestBody.get("name"),
					requestBody.get("email"), requestBody.get("contactNo"), requestBody.get("photoUrl")));
			return ResponseEntity.ok(new ResponseHelper<User>(HttpStatus.OK, "Success", newUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<User>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseHelper<User>> getUserById(@PathVariable String id) {
		try {
			User fetchedUser = userService.getUserByID(id);
			if (fetchedUser == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseHelper<User>(HttpStatus.NOT_FOUND, "User not found", null));
			}
			return ResponseEntity.ok(new ResponseHelper<User>(HttpStatus.OK, "Success", fetchedUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<User>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ResponseHelper<User>> getUserByEmail(@RequestParam String email) {
		try {
			User user = userService.getUserByEmail(email);
			if (user != null) {
				return ResponseEntity.ok(new ResponseHelper<User>(HttpStatus.OK, "Success", user));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseHelper<User>(HttpStatus.NOT_FOUND, "User not found", null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseHelper<User>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
		}
	}

	@PutMapping
	public ResponseEntity<ResponseHelper<User>> updateUserData(@RequestBody Map<String, String> requestBody) {
		try {
			User updatedUser = userService
					.updateUserDetails(new User(requestBody.get("userId"), requestBody.get("name"),
							requestBody.get("email"), requestBody.get("contactNo"), requestBody.get("photoUrl")));
			return ResponseEntity.ok(new ResponseHelper<User>(HttpStatus.OK, "Success", updatedUser));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseHelper<User>(HttpStatus.NOT_FOUND, e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseHelper<User>> deleteUser(@PathVariable String id) {
		User delUser = userService.deleteUserByID(id);
		if (delUser != null) {
			return ResponseEntity.ok(new ResponseHelper<User>(HttpStatus.OK, "Success", delUser));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseHelper<User>(HttpStatus.NOT_FOUND, "User doesn't exist", null));
		}
	}

	@PostMapping("/join-pg")
	public ResponseEntity<ResponseHelper<UserPG>> joinPg(@RequestBody Map<String, String> request) {
		try {
			UserPG userPG = userPGService.joinUserPG(request.get("userId"), request.get("pgId"));
			if (userPG != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseHelper<>(HttpStatus.OK, "Success", userPG));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ResponseHelper<>(HttpStatus.NOT_FOUND, "User doesn't exist for thr entered PG", null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

}
