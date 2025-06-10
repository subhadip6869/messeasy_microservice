package app.netlify.dsubha.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.netlify.dsubha.entity.UserPG;
import app.netlify.dsubha.entity.embeddable.UserPGId;

public interface UserPGRepository extends CrudRepository<UserPG, UserPGId> {

	@Query("SELECT u from UserPG u WHERE u.id.userId = :userId AND u.id.pgId = :pgId")
	UserPG findByPgAndUser(@Param("pgId") String pgId, @Param("userId") String userId);
}
