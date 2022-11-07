package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * Consumer内置函数式接口： 一个入参,没有出参
 *  消费型函数式接口
 *  这个接口还有自己的具体子接口可以使用
 */
public class ConsumerTest {

    @Test
    void test1(){
        Consumer<String> con1 = System.out::println;
        con1.accept("hahaha");
    }


    @Test
    void test2(){
        IntConsumer consumer1 = x -> System.out.println(x * 2);
        IntConsumer consumer2 = x -> System.out.println(x * 3);

        // 不是加法，是一个完了执行另一个
        consumer1.andThen(consumer2).accept(20);
    }


}
