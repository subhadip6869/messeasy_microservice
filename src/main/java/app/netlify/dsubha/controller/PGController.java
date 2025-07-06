package app.netlify.dsubha.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.dsubha.entity.PG;
import app.netlify.dsubha.entity.PGAdmin;
import app.netlify.dsubha.entity.User;
import app.netlify.dsubha.entity.UserPG;
import app.netlify.dsubha.helpers.ResponseHelper;
import app.netlify.dsubha.service.PGAdminService;
import app.netlify.dsubha.service.PGService;
import app.netlify.dsubha.service.UserPGService;
import app.netlify.dsubha.service.UserService;

@RestController
@RequestMapping("/pg")
public class PGController {

	@Autowired
	PGService pgService;

	@Autowired
	UserService userService;

	@Autowired
	PGAdminService pgAdminService;

	@Autowired
	UserPGService userPGService;

	@PostMapping
	public ResponseEntity<ResponseHelper<PG>> addNewPg(@RequestBody Map<String, String> body,
			@RequestHeader(value = "x-user-id", required = false) String userId) {
		try {
			User user = userService.getUserByID(userId);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseHelper<PG>(HttpStatus.NOT_FOUND, "User not found", null));
			}
			PG newPg = pgService.createNewPg(new PG(body.get("pgName"), body.get("address"), body.get("website"),
					body.get("countryCode"), body.get("timezone"), body.get("currency")));
			userPGService.assignUserToPg(userId, newPg.getPgId(), userId, true);
			pgAdminService.assignPGAdmin(userId, newPg.getPgId());
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseHelper<PG>(HttpStatus.OK, "Success", newPg));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<PG>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseHelper<PG>> fetchPGByID(@PathVariable(required = true) String id) {
		try {
			PG pg = pgService.getPGByID(id);
			if (pg != null) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseHelper<PG>(HttpStatus.OK, "Success", pg));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseHelper<PG>(HttpStatus.NOT_FOUND, "PG not found", null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseHelper<PG>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ResponseHelper<List<PG>>> fetchAllPGs() {
		try {
			List<PG> allPGs = pgService.getAllPGs();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseHelper<List<PG>>(HttpStatus.OK, "Total PG found: " + allPGs.size(), allPGs));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseHelper<List<PG>>(HttpStatus.NOT_FOUND, e.getMessage(), null));
		}
	}

	@PutMapping
	public ResponseEntity<ResponseHelper<PG>> updatePGDetaills(@RequestBody Map<String, String> body) {
		try {
			PG updated = pgService.updatePGDetails(body.get("pgId"), new PG(body.get("pgName"), body.get("address"),
					body.get("website"), body.get("countryCode"), body.get("timezone"), body.get("currency")));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseHelper<PG>(HttpStatus.OK, "Success", updated));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<PG>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseHelper<PG>> deletePGByID(@PathVariable(required = true) String id) {
		PG delPg = pgService.deletePGById(id);
		if (delPg != null) {
			return ResponseEntity.ok(new ResponseHelper<PG>(HttpStatus.OK, "Success", delPg));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseHelper<PG>(HttpStatus.NOT_FOUND, "PG doesn't exist", null));
		}
	}

	@PostMapping("/assign-admin")
	public ResponseEntity<ResponseHelper<PGAdmin>> assignPGAdmin(@RequestBody Map<String, String> request) {
		try {
			PGAdmin admin = pgAdminService.assignPGAdmin(request.get("userId"), request.get("pgId"));
			return ResponseEntity.ok(new ResponseHelper<PGAdmin>(HttpStatus.OK, "Success", admin));
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<PGAdmin>(HttpStatus.BAD_REQUEST, "PG is already owned by an admin", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<PGAdmin>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@PostMapping("/assign-pg")
	public ResponseEntity<ResponseHelper<UserPG>> assignUserToPG(@RequestBody Map<String, String> request,
			@RequestHeader(value = "x-user-id", required = true) String userId) {
		try {
			UserPG userPG = userPGService.assignUserToPg(request.get("userId"), request.get("pgId"), userId, false);
			return ResponseEntity.ok(new ResponseHelper<>(HttpStatus.OK, "PG assigned to user", userPG));
		} catch (DataIntegrityViolationException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<>(HttpStatus.BAD_REQUEST, "User already exist in this PG", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseHelper<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@PostMapping("/remove-pg")
	public ResponseEntity<ResponseHelper<User>> removeUserFromPG(@RequestBody Map<String, String> request,
			@RequestHeader(value = "x-user-id", required = true) String userId) {
		UserPG userPG = userPGService.removeUserFromPG(request.get("userId"), request.get("pgId"), userId);
		if (userPG != null) {
			return ResponseEntity
					.ok(new ResponseHelper<User>(HttpStatus.OK, "User Removed Successfully", userPG.getUser()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponseHelper<User>(HttpStatus.NOT_FOUND, "User doesn't exist for thr entered PG", null));
		}
	}

}
