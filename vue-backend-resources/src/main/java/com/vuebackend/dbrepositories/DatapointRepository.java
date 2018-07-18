package com.vuebackend.dbrepositories;

import com.vuebackend.entities.Datapoint;

import org.springframework.data.repository.CrudRepository;

public interface DatapointRepository extends CrudRepository<Datapoint, Long> {
}