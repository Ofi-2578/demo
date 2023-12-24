package com.example.demo.initialmessage;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InitialMessageRepository extends ReactiveMongoRepository<InitialMessage, Long> {

    @Override
    <S extends InitialMessage> Mono<S> insert(S entity);

    Flux<InitialMessage> findByReceiver(String receiverId);
}
