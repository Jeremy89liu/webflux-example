package com.niuniu.webflux.controller;

import com.niuniu.webflux.entity.User;
import com.niuniu.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    //注入Sevice
    @Autowired
    private UserService userService;

    //id查询
    @GetMapping("/user/{id}")
    public Mono<User> getUserId(@PathVariable int id){
        return userService.getUserById(id);
    }
    //查询所有
    @GetMapping("/user")
    public Flux<User> getUsers(){
            return userService.getAllUser();
    }
    //添加
    @PostMapping("/saveUser")
    public Mono<Void> saveUser(@RequestBody User user){
        Mono<User> userMono=Mono.just(user);
        return userService.savaUser(userMono);
    }
}
