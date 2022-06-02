package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.AbschlussEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = AbschlussEntity.class, idClass = Long.class)
public interface AbschlussRepository extends CrudRepository<AbschlussEntity, Long> {

}