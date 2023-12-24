package com.example.demo.chatroom;

import com.example.demo.services.CurrentUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RestController
@RequestMapping("/chat-room")
public class ChatRoomController {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatRoom> create(@RequestBody ChatRoom chatRoom){
        return CurrentUserId.getId()
                .flatMap(id -> {
                    chatRoom.members.add(id);
                    chatRoom.messages = new ArrayList<>();
                    return chatRoomRepository.insert(chatRoom);
        });
    }

    @PostMapping(value = "/{roomId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> send(@PathVariable String roomId, @RequestBody Message message){
        return CurrentUserId.getId()
                .flatMap(id -> {
                    message.setSender(id);
                    return chatRoomRepository
                            .updateMessages(roomId, id, message)
                            .onErrorReturn(-1L);
                })
                .map(c -> {
                    HttpStatus status = c == -1?
                            HttpStatus.CONFLICT
                            :
                            HttpStatus.NO_CONTENT;
                    return ResponseEntity.status(status).build();
                });
    }

    @GetMapping(value = "/{roomId}/{date}")
    public Flux<Message> getMessages(@PathVariable String roomId, @PathVariable long date){
        return CurrentUserId.getId()
                .flatMapMany(id -> chatRoomRepository.fetch(roomId, id, date))
                .flatMap(room -> Flux.fromIterable(room.messages));
    }
}