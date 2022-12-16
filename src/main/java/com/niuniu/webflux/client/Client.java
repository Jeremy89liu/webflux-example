package com.niuniu.webflux.client;

import com.niuniu.webflux.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Client {

    public static void main(String args[]){
        //调用服务器地址
        WebClient webClient=WebClient.create("http://localhost:59358");

        String id="1";
        User userResult =webClient.get().uri("/users/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(User.class).block();
        System.out.println(userResult.getName());
    }
}
