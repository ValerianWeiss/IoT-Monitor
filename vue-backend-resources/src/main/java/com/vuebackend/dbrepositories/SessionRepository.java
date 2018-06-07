package com.vuebackend.dbrepositories;

import java.util.Optional;

import com.vuebackend.entities.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
    public Optional<Session> findBySessionToken(String sessionToken);
}