package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserService userService;

    /**
     * @param username Username for which UserDetails are requested
     * @return UserDetails for the requested user
     * @throws UsernameNotFoundException Thrown when username wasn't found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userService.getUserByUserName(username);

        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with the name %s not found", username));
        }

        User currentUser = foundUser.get();

        return new org.springframework.security.core.userdetails.User(
                currentUser.getUserName(),
                currentUser.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(
                                String.format("%s%s", ROLE_PREFIX, currentUser.getRole().name())
                        )
                )
        );
    }
}
