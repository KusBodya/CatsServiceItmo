package ru.Bodyaaaa.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.Bodyaaaa.models.Master;
import ru.Bodyaaaa.repository.MasterRepository;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MasterRepository masterRepository;

    @Autowired
    public MyUserDetailsService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Master master = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                master.getUsername(),
                master.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(
                        master.getRole().name()
                ))
        );
    }
}
