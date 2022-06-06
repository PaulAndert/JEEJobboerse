package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.AbschlussEntity;
import de.thb.jee.authexample.repository.AbschlussRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AbschlussService {

    private final AbschlussRepository abschlussRepository;

    public List<AbschlussEntity> getAllAbschlüsse(){ return abschlussRepository.findAll(); }

    public AbschlussEntity getByMatchingName(String str){ return abschlussRepository.findAllByName(str); }
}