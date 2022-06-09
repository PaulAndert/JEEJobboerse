package de.thb.jee.authexample.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import de.thb.jee.authexample.entity.UserEntity;

@RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findUserEntitiesByEmail(String email);

	Optional<UserEntity> findByEmail(String email);

	@Query("select distinct u from user u \n" +
			"left join u.userAbschluesse a \n" +
			"left join u.userKompetenzen k \n" +
			"where u.roleId = 2 and u.beschreibung like :beschreibung \n" +
			"and ((:abschluesseid = 0) or (a.id = :abschluesseid))\n" +
			"and ((:kompetenzenid = 0) or (k.id = :kompetenzenid))")
	List<UserEntity> search
	(@Param("beschreibung") String beschreibung,
	 @Param("abschluesseid") int abschluesseid,
	 @Param("kompetenzenid") int kompetenzenid);

	List<UserEntity> findAll();
}
