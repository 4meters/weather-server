package com.weather.server.domain.repository;

import com.weather.server.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    User findByToken(String token);

    User findByApiKey(String apiKey);
}
