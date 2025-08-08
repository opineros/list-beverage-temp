package com.opineros.listbeverage.user.repository;

import com.opineros.listbeverage.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
}
