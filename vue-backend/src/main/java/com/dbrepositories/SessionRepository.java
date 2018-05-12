package com.dbrepositories;

import java.util.Optional;

import com.entities.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
    public Optional<Session> findBySessionToken(String sessionToken);
}