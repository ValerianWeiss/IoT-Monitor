package com.vuebackend.dbrepositories;

import java.util.Optional;

import com.vuebackend.entities.Endpoint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EndpointRepository extends CrudRepository<Endpoint, Long> {
    @Query("SELECT e FROM Endpoint e WHERE e.name = :name AND e.user.username = :username")
    public Optional<Endpoint> findByName(@Param("username") String username,
                                         @Param("name") String name);
}