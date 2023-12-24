package com.example.demo.chatroom;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
//           "{ '$match' : { 'members' : ?1 } } ",
//            " '$unwind' : { '$messages'} ",
//            " '$match' : { 'messages.date' : { '$gte': ?2 } } ",
//            " {'$group' : { '_id' ?0 , 'messages': {'$push': '$messages' } } }"


public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom ,Long> {

    //@Query(value = Queries.FindByChatIdAndUserIsMember, fields = Queries.AllMessagesAfterDate)
    @Aggregation(pipeline = {
            " { '$match' : { '_id' : ?0 } }",
            " { '$match' : { 'members' : ?1 } } ",
            " { '$unwind': '$messages' } ",
            " { '$match' : { 'messages.date' : { '$gte': ?2 } } }",
            "{'$group': {'_id': ?0, 'messages': {'$push': '$messages'}}}"
    })
    public Mono<ChatRoom> fetch(String chatRoomId, String userId, long date);

    @Query(Queries.FindByChatIdAndUserIsMember)
    @Update("{ '$push' : { 'messages' : ?2 } }")
    public Mono<Long> updateMessages(String chatRoomId, String userId, Message message);

    Mono<ChatRoom> findById(String id);
}

class ChatRoomAggreag{
    String id;
    public ChatRoomAggreag(String id){
        this.id = id;
    }
}

class Queries{
    protected final static String FindByChatIdAndUserIsMember = """
            { '_id' : ?0,
            'members' : ?1
            }
            """;
    protected final static String AllMessagesAfterDate = """
                    {
                    'userId' : 1,
                    'messages' : {
                        '$elemMatch' : {
                            'date' : {
                                '$gt' : ?2
                                }
                            }
                        }
                    }
            """;
}
