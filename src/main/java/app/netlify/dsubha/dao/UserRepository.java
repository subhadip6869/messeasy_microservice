package app.netlify.dsubha.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.netlify.dsubha.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
	List<User> findByEmail(String email);
}
