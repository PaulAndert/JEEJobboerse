package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.KompetenzenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = KompetenzenEntity.class, idClass = Long.class)
public interface KompetenzenRepository extends CrudRepository<KompetenzenEntity, Long> {
    List<KompetenzenEntity> findAll();
    KompetenzenEntity findByName(String str);
    KompetenzenEntity findById(long id);
}