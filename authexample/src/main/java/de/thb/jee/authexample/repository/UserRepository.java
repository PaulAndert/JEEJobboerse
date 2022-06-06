package de.thb.jee.authexample.repository;

import java.util.List;
import java.util.Optional;

import de.thb.jee.authexample.entity.AbschlussEntity;
import de.thb.jee.authexample.entity.KompetenzenEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import de.thb.jee.authexample.entity.UserEntity;

@RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findUserEntitiesByEmail(String email);
	Optional<UserEntity> findByEmail(String email);
	List<UserEntity> findUserEntitiesByEmailContains(String str);
	/*
	@Query("select distinct from user u \n" +
			"join user_besitzt_abschluss uba on u.id = uba.user_id \n" +
			"join abschluss a on uba.abschluss_id = a.id \n" +
			"join user_besitzt_kompetenz ubk on u.id = ubk.user_id \n" +
			"join kompetenzen k on ubk.kompetenz_id = k.id \n" +
			"where u.role_id = 2 and u.beschreibung like '%?1%' " +
			"and uba.abschluss_id = ?2 or true " +
			"and ubk.kompetenz_id = ?3 or true ")
	List<UserEntity> search
	(String beschreibung, long Abschluesseid, long Kompetenzenid);
	*/
	List<UserEntity> findUserEntitiesByBeschreibungContainsAndRoleId(String str, int roleId);
	List<UserEntity> findUserEntitiesByRoleId(int roleId);
	List<UserEntity> findAll();
}
