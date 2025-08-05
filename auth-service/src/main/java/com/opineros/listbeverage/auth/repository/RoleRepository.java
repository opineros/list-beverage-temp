package com.opineros.listbeverage.auth.repository;

import com.opineros.listbeverage.auth.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
