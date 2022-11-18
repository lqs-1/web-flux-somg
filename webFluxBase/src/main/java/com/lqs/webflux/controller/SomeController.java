package com.lqs.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.concurrent.RunnableFuture;

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

    // sse

    /**
     * sse:服务端推送事件
     *
     * sse和webSocket对比：
     *  webSocket：双工通道，服务端和客户端相互发送消息
     *  sse：单工，服务端向客户端发送消息，适用于一个请求会对应多个响应的
     *
     * sse技术规范：
     *  1、服务端与浏览器之间的通讯协议
     *  2、浏览器中可供js使用的EventSource对象
     *
     *  通讯协议：基于纯文本的简单协议，服务端相应内容类型必须是“text/event-stream”（事件流[文本流]），且只支持UTF-8编码格式
     *          事件流由不同的时间组成，不同事物间通过仅包含回车符加换行符的空行（“\r\n ”）来分隔
     *          每个时间可以由多行构成，每行由 数据类型：值 构成 每个事件可以包含以下类型：
     *              data：表示事件包含的数据，可以出现多次
     *              event：表示事件名称，浏览器在收到数据时，会产生对应名称的事件
     *              空白：表示注释，处理的时候被忽略
     *              retry：表示浏览器断开链接之后重链的等待时间
     *              id：表示时间的标识符，标识符用于链接中断后的续连
     *
     *  EventSource对象：浏览器收到服务器发送的带有事件的响应，浏览器就会在js中使用EventSource对象进行处理
     *                  EventSource使用的是标准的时间监听方式：
     *                      1、当浏览器成功与服务器建立链接时触发 open事件 执行onopen()方法处理
     *                      2、当收到服务端发送的事件时触发 message事件 执行onmessage()和addEventListener()方法处理，两个方法差不多
     *                      3、当发生异常时触发 error事件 执行onerror()方法处理
     *  sse使用：
     *      一样的写请求路径，多一个produces="text/event-stream"，字符编码为UTF-8,页面访问的时候不是直接写我请求地址，而是通过EventSource来访问的
     *
     * @return
     */
    @GetMapping(value = "sse", produces = "text/event-stream")
    public Flux<String> sseFlux(){
        // Flux表示0个或者n个元素的异步排序
        return Flux.just("haha", "hehe");
    }


    /**
     * 反应式编程(Reactive Programming)库：RxJava、RxJava2、Reactor
     *
     * 概念：
     *  Reactive Stream: 是反应式流编程规范
     *  RxJava：是反应式流编程库，产生于Reactive Stream之前，不遵循Reactive Stream规则，使用很不方便
     *  RxJava2：是反应式流编程库，产生于Reactive Stream之后，
     *  Reactor：是完全基于Reactive Stream规范的，使用方便
     *
     * Reactive Stream规范：
     *  Publisher(发布者、生产者):订阅者的消息生产者，订阅者接收消息的顺序和生产者生产的顺序一致（用过订阅者的onNext方法），当订阅者触发onError方法之后，就不再接收消息（发布者消息被删除或者发生错误，比如生产者发生了不允许发布给订阅者）
     *            当生产者执行了close方法之后，就表示没有消息在发布了，订阅者将触发onComplete方法，生产这可以确保每一个订阅者的订阅方法都严格按照顺序执行。
     *              Subscribe：该方法用于确立当前发布者与指定订阅者的订阅关系，订阅者的onSubscribe方法会随着新的订阅关系而被调用，订阅者可以通过Subscription订阅令牌的request方法来接收消息，也可以通过cancel方法来取消订阅关系
     *
     *  Subscriber(订阅者):onSubscribe方法：当publisher调用了subscribe方法与指定的订阅者确立了订阅关系之后，就会触发这个方法，且只会执行一次。
     *                    onNext方法：当前的消息被消费完成之后，获取下一个消息，同时onNext也是消费方法，一个onNext执行完成就会执行下一个onNext方法，onNext被触发的次数由Subscriber调用Subscription的request请求的消息数量决定，第一次onNext方法是由Subscription的request方法触发
     *                    onError方法：这个方法也只会执行一次，触发onError方法的事件：
     *                          发布者发布的消息不是订阅者的需求时
     *                          在确定订阅关系时出现异常
     *                          当Subscription的request方法参数小于等于0时
     *                    onComplete方法：不是错误终止的订阅关系，没有消息发布的时候就会执行，且执行一次
     *
     *  Subscription(发布者与订阅者的消息控制器):订阅令牌或者订阅关系，只有当请求时订阅者才可以接收消息，并且是任何时间都可以取消，这个接口中的方法只能被它的订阅者调用
     *                                      Subscription的方法只能被订阅者Subscriber调用，作为Subscriber中的对象出现
     *                                      request：给未满足需求（有一个空间[缓存]，订阅令牌的大小，如果空间是充足的就是未满足，比如大小是32,而传递的是50,那么也是返回32个，32是空间未满足的情况下放的）的订阅令牌添加指定数量n个消息
     *                                              n小于等于0执行onError，否则正常执行n次onNext（n次或者更少）
     *                                      cancel:订阅者尽量不接收消息，可能调用之后onNext还会执行几次，但是绝不会长时间执行，该方法的执行原理上会导致onNext方法的停止，但实际有可能onNext并没有马上停止
     *
     *  Processor(可以做发布者也可以做订阅者的处理器)
     *
     *  SubmissionPublisher(发布者类)：实现一个发布者的类，以异步的方式将已被提交爱你的非空元素发布给订阅者，直到该发布者关闭，发布者通过丢弃处理和/或阻塞来进行流量控制
     *                               submit：将给定的消息发布到每个当前的订阅者，通过异步的方式调用onNext方法，当任何为订阅者准备的资源不可用时会阻塞，并且阻塞将不会被打断。当publisher的缓存满时，该方法会阻塞
     *                               close：除非已经关闭，否则会向当前所有订阅者发出执行onComplete方法的信号，并且不允许后续发布尝试，返回时，该方法不保证所有订阅者都已完成，也就是说各个订阅者是否真正能够执行onComplete方法
     *                                      当前无法保证，与订阅者的具体执行情况相关
     *
     */

}
