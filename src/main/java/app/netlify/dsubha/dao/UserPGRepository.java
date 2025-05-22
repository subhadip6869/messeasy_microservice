package app.netlify.dsubha.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.dsubha.entity.UserPG;
import app.netlify.dsubha.entity.embeddable.UserPGId;

public interface UserPGRepository extends CrudRepository<UserPG, UserPGId> {

}
