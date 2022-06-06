package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.AbschlussEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = AbschlussEntity.class, idClass = Long.class)
public interface AbschlussRepository extends CrudRepository<AbschlussEntity, Long> {
    List<AbschlussEntity> findAll();
    AbschlussEntity findAllByName(String str);
}