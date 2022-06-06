package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.KompetenzenEntity;
import de.thb.jee.authexample.repository.KompetenzenRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KompetenzService {

    private final KompetenzenRepository kompetenzenRepository;

    public List<KompetenzenEntity> getAllKompetenzen(){
        return kompetenzenRepository.findAll();
    }

    public KompetenzenEntity getByMatchingName(String str){ return kompetenzenRepository.findAllByName(str); }
}