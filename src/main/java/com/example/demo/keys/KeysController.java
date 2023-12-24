package com.example.demo.keys;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/keys")
public class KeysController {

    @Autowired
    KeysRepository keysRepository;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> storeKeys(@RequestBody PublicKeys keys){
        Mono<String> getId = ReactiveSecurityContextHolder.getContext().map(s -> s.getAuthentication().getName());
        return getId.map(id -> {
            keys.setUserId(id);
            return keys;
        }).flatMap(publicKeys -> keysRepository.insert(publicKeys).onErrorReturn(new EmptyKeys()))
                .flatMap(pubKeys -> {
                    HttpStatus status = pubKeys instanceof EmptyKeys ?
                            HttpStatus.CONFLICT
                            :
                            HttpStatus.NO_CONTENT;
                    return Mono.just(ResponseEntity.status(status).build());
                });
    }

    @GetMapping(value = "/{id}")
    @JsonView(PublicKeys.PublicKeysView.class)
    public Mono <PublicKeys> getKeys(@PathVariable String id){
        return this.keysRepository.findById(id);
    }
}
