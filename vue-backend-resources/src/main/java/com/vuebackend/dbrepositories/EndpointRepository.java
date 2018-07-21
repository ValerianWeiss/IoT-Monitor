package com.vuebackend.dbrepositories;

import java.util.Optional;

import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.Sensor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EndpointRepository extends CrudRepository<Endpoint, Long> {
    @Query("SELECT e FROM Endpoint e WHERE e.name = :name AND e.user.username = :username")
    public Optional<Endpoint> findByName(@Param("username") String username,
                                         @Param("name") String name);

    @Query("SELECT s FROM Sensor s WHERE s.endpoint.id = (SELECT id FROM Endpoint e WHERE e.name = :endpointName AND e.user.id = (SELECT id FROM User u WHERE u.username = :username))")
    public Iterable<Sensor> getSensors(@Param("username") String username,
                                       @Param("endpointName") String endpointName);                                   
}