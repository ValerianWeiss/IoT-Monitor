package com.vuebackend.dbrepositories;

import com.vuebackend.entities.Endpoint;

import org.springframework.data.repository.CrudRepository;

public interface EndpointRepository extends CrudRepository<Endpoint, Long> {
}