package com.wwi21sebgroup5.cinema.config;

import com.wwi21sebgroup5.cinema.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

}
