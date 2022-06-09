package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.OffeneStellenEntity;
import de.thb.jee.authexample.repository.OffeneStellenRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OffeneStellenService {

    private final OffeneStellenRepository offeneStellenRepository;

    public List<OffeneStellenEntity> getAllOffeneStellenOfUser(long id){
        return offeneStellenRepository.findOffeneStellenEntitiesByUserId(id);
    }

    public List<OffeneStellenEntity> loadAllOffeneStellenMatchingSeachParameters(int gehalt, String desc, int KompetenzenId){
        return offeneStellenRepository.search(gehalt, desc, KompetenzenId);
    }
 }
