package com.dell.ehealthcare.repository;

import com.dell.ehealthcare.model.Role;
import com.dell.ehealthcare.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
