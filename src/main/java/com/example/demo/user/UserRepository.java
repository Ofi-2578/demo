package com.example.demo.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {
    Mono<User> findByUsername(String username);

    <S extends User> Mono<S> save(S entity);

    <S extends User> Mono<S> findById(String name);
}
