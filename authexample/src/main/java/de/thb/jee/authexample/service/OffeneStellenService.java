package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.OffeneStellenEntity;
import de.thb.jee.authexample.repository.OffeneStellenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OffeneStellenService {

    private final OffeneStellenRepository offeneStellenRepository;

    public OffeneStellenEntity getOffeneStelleById(long id){
        return offeneStellenRepository.findOffeneStellenEntitiesById(id);
    }

    public List<OffeneStellenEntity> getAllOffeneStellenOfUser(long id){
        return offeneStellenRepository.findOffeneStellenEntitiesByUserId(id);
    }

    public List<OffeneStellenEntity> loadAllOffeneStellenMatchingSeachParameters(int gehalt, String desc, int KompetenzenId){
        return offeneStellenRepository.search(gehalt, desc, KompetenzenId);
    }

    public List<OffeneStellenEntity> loadAll(){
        return offeneStellenRepository.findAll();
    }

    public void deleteById(long id){
        offeneStellenRepository.deleteById(id);
    }

    public void insert(OffeneStellenEntity ofe){
        offeneStellenRepository.save(ofe);
    }
 }
