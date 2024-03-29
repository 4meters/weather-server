package com.weather.server.domain.repository;

import com.weather.server.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);

    User findByToken(String token);

    User findByUserId(String userId);

    void deleteByUserId(String userId);
}
