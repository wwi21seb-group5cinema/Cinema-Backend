package com.wwi21sebgroup5.cinema.services;

import com.wwi21sebgroup5.cinema.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

}
