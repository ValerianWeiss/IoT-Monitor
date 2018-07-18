package com.vuebackend.dbrepositories;

import java.util.Optional;

import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUsername(String username);
   
    @Query("SELECT e FROM Endpoint e WHERE e.name = :endpointName AND e.user.id = (SELECT u.id FROM User u WHERE u.username = :username)")
    public Optional<Endpoint> findEndpointByNameOfUser(@Param("username") String username,
                                                       @Param("endpointName") String endpointName);
}