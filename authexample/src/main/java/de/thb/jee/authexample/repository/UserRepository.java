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
//	@Query("SELECT n FROM UserEntity n WHERE n.email = :email")
//	Optional<UserEntity> findByEmail(@Param("email") String email);
	
	Optional<UserEntity> findByEmail(String email);
	List<UserEntity> findUserEntitiesByEmailContains(String str);
	List<UserEntity> findUserEntitiesByAbschluesseContains(String str);
}
