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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.dsubha.entity.PG;
import app.netlify.dsubha.entity.PGAdmin;
import app.netlify.dsubha.helpers.ResponseHelper;
import app.netlify.dsubha.service.PGAdminService;
import app.netlify.dsubha.service.PGService;

@RestController
@RequestMapping("/pg")
public class PGController {

	@Autowired
	PGService pgService;

	@Autowired
	PGAdminService pgAdminService;

	@PostMapping
	public ResponseEntity<ResponseHelper<PG>> addNewPg(@RequestBody PG pg) {
		try {
			PG newPg = pgService.createNewPg(pg);
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
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseHelper<PG>(HttpStatus.OK, "Success", pg));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseHelper<PG>(HttpStatus.NOT_FOUND, e.getMessage(), null));
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
	public ResponseEntity<ResponseHelper<PG>> updatePGDetaills(@RequestBody PG pg) {
		try {
			PG updated = pgService.updatePGDetails(pg);
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

}
