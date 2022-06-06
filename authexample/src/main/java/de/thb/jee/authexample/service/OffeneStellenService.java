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
    public List<OffeneStellenEntity> loadAllbyGehaltAndBeschreibung(long gehalt, String desc){
        return offeneStellenRepository.findOffeneStellenEntitiesByGehaltGreaterThanEqualAndBeschreibungContains(gehalt, desc);
    }
    public List<OffeneStellenEntity> loadAllbyGehalt(long gehalt){
        return offeneStellenRepository.findOffeneStellenEntitiesByGehaltGreaterThanEqual(gehalt);
    }
    public List<OffeneStellenEntity> loadAllbyBeschreibung(String desc){
        return offeneStellenRepository.findOffeneStellenEntitiesByBeschreibungContains(desc);
    }
    public List<OffeneStellenEntity> loadAllOffeneStellen(){
        return offeneStellenRepository.findAll();
    }
 }
