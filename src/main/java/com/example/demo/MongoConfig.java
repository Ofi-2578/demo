package com.example.demo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Override
    public String getDatabaseName() {
        return "test_database";
    }

    public @Bean MongoClient mongoClient() {
        return this.reactiveMongoClient();
    }



}
