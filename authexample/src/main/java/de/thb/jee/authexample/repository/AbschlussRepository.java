package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface AbschlussRepository extends CrudRepository<UserEntity, Long> {
//	@Query("SELECT n FROM UserEntity n WHERE n.email = :email")
//	Optional<UserEntity> findByEmail(@Param("email") String email);

}