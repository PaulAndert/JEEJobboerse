package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.OffeneStellenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = OffeneStellenEntity.class, idClass = Long.class)
public interface OffeneStellenRepository extends CrudRepository<OffeneStellenEntity, Long> {
    List<OffeneStellenEntity> findOffeneStellenEntitiesByUserId(long id);
}
