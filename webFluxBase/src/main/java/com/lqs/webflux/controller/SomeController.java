package com.lqs.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName 模块名字
 * @date 2022/11/8 下午1:25
 * @do 一句话描述该模块的功能
 */
@Slf4j
@RestController
public class SomeController {


    private String doSome(String name){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return name;
    }

    @GetMapping("common")
    public String commonHandle(){
        log.info("common start");
        long startTime = System.currentTimeMillis();
        String common = doSome("common");
        long currentTime = System.currentTimeMillis();
        log.info("common end");
        System.out.println(currentTime - startTime);
        return common;
    }

    @GetMapping("mono")
    public Mono<String> monoHandle(){
        log.info("mono start");
        long startTime = System.currentTimeMillis();
        Mono<String> mono = Mono.fromSupplier(() -> doSome("mono"));
        log.info("mono end");

        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - startTime);
        // Mono表示0个或者1个元素的异步排序
        return mono;
    }

    @GetMapping("flux")
    public Flux<String> fluxHandle(){
        // Flux表示0个或者n个元素的异步排序
        return Flux.just("haha", "hehe");
    }


    @GetMapping("array")
    public Flux<String> arrayHandle(@RequestParam("paramArray") String[] paramArray){
        // Flux表示0个或者n个元素的异步排序
        // 将数组转为Flux
        return Flux.fromArray(paramArray);
    }


    @GetMapping("list")
    public Flux<String> listHandle(@RequestParam("paramList") List<String> paramList){
        // Flux表示0个或者n个元素的异步排序
        // 将集合转为Flux
        return Flux.fromStream(paramList.stream());
    }

    // 耗时操作
    @GetMapping("time")
    public Flux<String> timeHandle(@RequestParam("paramList") List<String> paramList){
        // Flux表示0个或者n个元素的异步排序
        // 将集合转为Flux
        log.info("time start");
        long startTime = System.currentTimeMillis();
        Flux<String> rst = Flux.fromStream(paramList.stream().map(str -> doSome("ele-" + str)));
        log.info("time end");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        return rst;
    }


}
