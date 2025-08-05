package com.opineros.listbeverage.auth.repository;

import com.opineros.listbeverage.auth.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
