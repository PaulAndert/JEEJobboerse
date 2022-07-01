package de.thb.jee.authexample.repository;

import de.thb.jee.authexample.entity.OffeneStellenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = OffeneStellenEntity.class, idClass = Long.class)
public interface OffeneStellenRepository extends CrudRepository<OffeneStellenEntity, Long> {

    OffeneStellenEntity findById(long id);
    List<OffeneStellenEntity> findAllByUserId(long id);
    void deleteById(long id);
    @Query("select distinct o_f from offene_stelle o_f \n" +
            "left join o_f.offeneStelleKompetenzen k \n" +
            "where o_f.beschreibung like :beschreibung \n" +
            "and ((:gehalt = 0) or (o_f.gehalt >= :gehalt)) \n" +
            "and ((:kompetenzenid = 0) or (k.id = :kompetenzenid))" +
            "and o_f.enabled = true")
    List<OffeneStellenEntity> search(@Param("gehalt") int gehalt,
                                     @Param("beschreibung") String desc,
                                     @Param("kompetenzenid") int kompetenzenId);
}
