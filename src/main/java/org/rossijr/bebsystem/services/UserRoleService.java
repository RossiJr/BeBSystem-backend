package org.rossijr.bebsystem.services;

import org.rossijr.bebsystem.models.UserRole;
import org.rossijr.bebsystem.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
