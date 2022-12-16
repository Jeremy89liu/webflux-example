package com.niuniu.webflux.service;

import com.niuniu.webflux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    //根据Id查询用户信息
    Mono<User> getUserById(Integer id);

    //查询所有用户信息
    Flux<User> getAllUser();

    //新增用户信息
    Mono<Void> savaUser(Mono<User> userMono);
}
