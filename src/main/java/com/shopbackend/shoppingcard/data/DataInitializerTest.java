package com.shopbackend.shoppingcard.data;

import com.shopbackend.shoppingcard.Model.Role;
import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Locale.filter;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializerTest implements ApplicationListener<ApplicationReadyEvent> {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream().filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);

    }

    private void createDefaultUserIfNotExists() {

        Role userRole = roleRepository.findByName("ROLE_USER").get();

        for (int i=1; i<=5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail))
                continue;
            User user = new User();
            user.setFirst_name("User");
            user.setLast_name("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("User " + i + " created");
        }
    }
    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();

        for (int i=1; i<=2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail))
                continue;
            User user = new User();
            user.setFirst_name("Admin");
            user.setLast_name("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Admin " + i + " created");
        }
    }}


