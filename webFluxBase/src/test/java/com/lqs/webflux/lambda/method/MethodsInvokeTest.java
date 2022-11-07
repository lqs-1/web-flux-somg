package com.lqs.webflux.lambda.method;


import com.lqs.webflux.lambda.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * 使用lambda对方法引用
 */
public class MethodsInvokeTest {

    /**
     * 引用静态方法
     */
    @Test
    void test1(){
        Consumer<Integer> consumer = Person::sleep;
        consumer.accept(123);
    }



    /**
     * 引用普通方法方法
     */
    @Test
    void test2(){
        Person person = new Person();
        Consumer<Integer> consumer = person::eat;
        consumer.accept(123);
    }


}
