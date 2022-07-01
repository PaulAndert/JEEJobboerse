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

    public OffeneStellenEntity loadById(long id){ return offeneStellenRepository.findById(id); }

    public List<OffeneStellenEntity> loadAllByUserId(long id){ return offeneStellenRepository.findAllByUserId(id); }

    public void deleteById(long id){
        offeneStellenRepository.deleteById(id);
    }

    public List<OffeneStellenEntity> loadSearchResults(int gehalt, String desc, int KompetenzenId) { return offeneStellenRepository.search(gehalt, desc, KompetenzenId); }

    public void insert(OffeneStellenEntity ofe){
        offeneStellenRepository.save(ofe);
    }
}

