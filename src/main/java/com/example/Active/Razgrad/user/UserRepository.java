package com.example.Active.Razgrad.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(@Param("userNameOrEmail") String userNameOrEmail);
    public User getUserByEmail(@Param("userNameOrEmail") String userNameOrEmail);
}
