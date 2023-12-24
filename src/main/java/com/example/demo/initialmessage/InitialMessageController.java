package com.example.demo.initialmessage;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/initial-message")
public class InitialMessageController {

    @Autowired
    InitialMessageRepository initialMessageRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> postMessage(@RequestBody InitialMessage initialMessage){
        return initialMessageRepository
                .insert(initialMessage)
                .onErrorReturn(new EmptyInitialMessage())
                .flatMap(initialMessage1 -> {
                    HttpStatus status = initialMessage1 instanceof EmptyInitialMessage ?
                            HttpStatus.CONFLICT
                            :
                            HttpStatus.NO_CONTENT;
                    return Mono.just(ResponseEntity.status(status).build());
                });
    }

    @GetMapping
    @JsonView(InitialMessage.InitialMessageView.class)
    public Flux<InitialMessage> getMessage(){
        Mono<String> getId = ReactiveSecurityContextHolder.getContext().map(s -> s.getAuthentication().getName());
        return getId.flatMapMany(s -> initialMessageRepository.findByReceiver(s));
    }
}
