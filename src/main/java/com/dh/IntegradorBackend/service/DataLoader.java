package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.AppUser;
import com.dh.IntegradorBackend.entities.AppUsuarioRoles;
import com.dh.IntegradorBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  implements ApplicationRunner{

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode("digital");
        String passs = passwordEncoder.encode("digitalputos");
        userRepository.save(new AppUser("Franco", "Veron", "franco@gmail.com", pass, AppUsuarioRoles.ROLE_USER ));
        userRepository.save(new AppUser("Admin", "admin" ,"admin@email.com", passs, AppUsuarioRoles.ROLE_ADMIN));
    }
}
