package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.AbschlussEntity;
import de.thb.jee.authexample.repository.AbschlussRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AbschlussService {

    private final AbschlussRepository abschlussRepository;

    public List<AbschlussEntity> loadAll(){ return abschlussRepository.findAll(); }

    public AbschlussEntity loadByName(String str){ return abschlussRepository.findByName(str); }

    public AbschlussEntity loadById(long id){
        return abschlussRepository.findById(id);
    }
}