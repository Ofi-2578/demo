package com.example.demo.keys;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

public interface KeysRepository extends ReactiveMongoRepository<PublicKeys, Long> {
    Mono<PublicKeys> findById(String userId);
    <S extends PublicKeys> Mono<S> save(S entity);
}
