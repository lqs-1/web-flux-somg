package com.lqs.streamreactive.pps;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * @author 李奇凇
 * @moduleName SomeTest
 * @date 2022/11/18 下午1:26
 * @do 发布者测试
 */
public class SomeTest {

    public static void main(String[] args) {

        /**
         * 背压：SubmissionPublisher发布者buffer最大256，初始32, Subscription订阅令牌里buffer最大256, 初始32
         *      当发布者的生产消息速度过快，很快就填满了Subscription的最大buffer，那么这个时候Subscription就阻塞着，欣慰生产者过快
         *      很快也填满了生产者的最大buffer，这个时候，生产者的生产将依托于订阅者，订阅者拿一个，生产者就生产一个
         */



        // 创建发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        try {

            // 创建处理器
            SomeProcessor processor = new SomeProcessor();

            // 创建订阅者
            SomeSubscriber subscriber = new SomeSubscriber();


            /**
             * 处理器：
             *  先作为订阅者，订阅最开始的消息
             *  再作为发布者，将订阅到的消息进行处理，然后发布给下游订阅者
             */

            // 建立订阅关系
            publisher.subscribe(processor);
            processor.subscribe(subscriber);

            // 生产消息
            for (int i = 0; i < 300; ++i) {
                // 生成一个[0.100)的随机数
                int randomInt = new Random().nextInt(100);

                // 发布消息,256个，满了就阻塞
                publisher.submit(randomInt);

            }
        }finally {
            // 当所有的消息发布完毕 关闭发布者
            publisher.close();
        }

        try {
            System.out.println("住线程开始等待");
            TimeUnit.SECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
