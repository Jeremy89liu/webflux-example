package com.niuniu.webflux.handler;

import com.niuniu.webflux.entity.User;
import com.niuniu.webflux.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService){

        this.userService = userService;
    }

    /**
     * 根据userId查询
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserById(ServerRequest request){
       Integer UserId= Integer.valueOf(request.pathVariable("id"));
       Mono<ServerResponse> notFound = ServerResponse.notFound().build();
       Mono<User> userMono =userService.getUserById(UserId);
        return userMono.
                flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(person)).switchIfEmpty(notFound)
        );
    }

    /**
     * 查询所有
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUsers(ServerRequest request){
        Flux<User> users = userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users,User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request){
        Mono<User> userMono =request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.savaUser(userMono));
    }

}
