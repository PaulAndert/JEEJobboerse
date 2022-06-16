package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.KompetenzenEntity;
import de.thb.jee.authexample.repository.KompetenzenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KompetenzService {

    private final KompetenzenRepository kompetenzenRepository;

    public List<KompetenzenEntity> getAllKompetenzen(){
        return kompetenzenRepository.findAll();
    }

    public KompetenzenEntity getByMatchingName(String str){ return kompetenzenRepository.findAllByName(str); }
}