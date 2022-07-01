package de.thb.jee.authexample.service;

import de.thb.jee.authexample.entity.UserEntity;
import de.thb.jee.authexample.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity loadByEmail(String email) throws UsernameNotFoundException { return userRepository.findByEmail(email); }

    public UserEntity loadById(long id) throws UsernameNotFoundException { return userRepository.findById(id); }

    public List<UserEntity> loadSearchResults(String desc, int abschlussId, int kompetenzId) { return userRepository.search(desc, abschlussId, kompetenzId); }
}
