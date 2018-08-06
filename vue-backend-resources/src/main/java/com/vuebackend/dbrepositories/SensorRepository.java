package com.vuebackend.dbrepositories;

import java.util.Optional;
import java.util.Set;

import com.vuebackend.entities.Sensor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    @Query("SELECT s FROM Sensor s WHERE s.name = :sensorName AND s.endpoint.name = :endpointName AND s.endpoint.user.username = :username")
    public Optional<Sensor> findByName(@Param("username") String username,
                                       @Param("endpointName") String endpointName,
                                       @Param("sensorName") String sensorName);

    public Set<Sensor> findAllByEndpointId(long endpointId);                                       
}