package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.KompetenzenEntity;
import de.thb.jee.authexample.repository.KompetenzenRepository;
import lombok.AllArgsConstructor;
import org.hibernate.dialect.LobMergeStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KompetenzService {

    private final KompetenzenRepository kompetenzenRepository;

    public List<KompetenzenEntity> loadAll(){
        return kompetenzenRepository.findAll();
    }

    public KompetenzenEntity loadByName(String str){ return kompetenzenRepository.findByName(str); }

    public KompetenzenEntity loadById(long id){
        return kompetenzenRepository.findById(id);
    }
}