package com.niuniu.webflux.service.impl;

import com.niuniu.webflux.entity.User;
import com.niuniu.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Integer,User> users= new HashMap<>();

    public UserServiceImpl(){
        this.users.put(1, User.builder().age(12).gender("female").name("test1").build());
        this.users.put(2, User.builder().age(20).gender("male").name("test2").build());
        this.users.put(3, User.builder().age(22).gender("female").name("test1").build());
    }

    @Override
    public Mono<User> getUserById(Integer id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> savaUser(Mono<User> userMono) {
        return userMono.doOnNext(person->{
            int id =users.size()+1;
            users.put(id,person);
        }).thenEmpty(Mono.empty());
    }
}
