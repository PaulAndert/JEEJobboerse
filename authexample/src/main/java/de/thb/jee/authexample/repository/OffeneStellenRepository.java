package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.OffeneStellenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass = OffeneStellenEntity.class, idClass = int.class)
public interface OffeneStellenRepository extends CrudRepository<OffeneStellenEntity, Integer> {

}
