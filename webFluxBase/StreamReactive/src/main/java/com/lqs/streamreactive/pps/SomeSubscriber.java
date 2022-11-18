package com.lqs.streamreactive.pps;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * @author 李奇凇
 * @moduleName SomeSubscriber
 * @date 2022/11/18 下午1:13
 * @do 创建订阅者
 */
public class SomeSubscriber implements Flow.Subscriber<String> {

    // 声明订阅令牌
    private Flow.Subscription subscription;


    // 当订阅关系确立的时候执行 publisher的subscribe方法的执行会触发这个方法
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("===执行订阅者的onSubscribe方法==");

        this.subscription = subscription;

        // 首次订阅8条消息
        this.subscription.request(8);
    }

    // 对消息的消费过程就在这里
    // 该方法的第一次触发是由onSubscribe方法中的this.subscription.request(8)触发
    @Override
    public void onNext(String str) {
        System.out.println("订阅者正在消费" + str);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 订阅者每消费一条就会再订阅10条消息   订阅快消费慢
        this.subscription.request(10);
    }


    // 当订阅关系确立过程中，订阅这请求消息过程中，消费者消费过程中过程中，发布者发布过程中出现异常，就会执行该方法
    @Override
    public void onError(Throwable throwable) {
        System.out.println("onerror执行");
        throwable.printStackTrace();

        // 取消订阅关系
        this.subscription.cancel();
    }


    // 当所有消息消费完成之后  发布者已关闭  执行
    @Override
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将消息消费完");
    }
}
