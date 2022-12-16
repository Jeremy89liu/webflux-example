package com.niuniu.webflux.server;

import com.niuniu.webflux.handler.UserHandler;
import com.niuniu.webflux.service.UserService;
import com.niuniu.webflux.service.impl.UserServiceImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Server {

    public static void main(String args[]) throws IOException {
        Server  server= new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    //创建Router路由
    public RouterFunction<ServerResponse> routerFunction(){
       //创建handler对象
        UserService service = new UserServiceImpl();
        UserHandler handler = new UserHandler(service);
       return RouterFunctions.route(
               GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById
       ).andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getUsers);
    }

    //创建服务器完成适配
    public void createReactorServer(){
        RouterFunction<ServerResponse> route = routerFunction();
        HttpHandler httpHandler= toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        //创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.port(8082);
        httpServer.handle(adapter).bindNow();
    }

}
