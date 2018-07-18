package com.vuebackend.dbrepositories;

import java.util.Optional;

import com.vuebackend.entities.Sensor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    @Query("SELECT s FROM Sensor s WHERE s.name = :name AND s.endpoint.name = :deviceName")
    public Optional<Sensor> findByName(@Param("deviceName") String deviceName,
                                       @Param("name") String name);
}