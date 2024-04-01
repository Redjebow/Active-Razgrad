package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends CrudRepository<Community, Long> {
    public Community getCommunityByUsername(@Param("userNameOrEmail") String userNameOrEmail);

}
