package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

/**
 * BiFunction内置函数式接口： 两个入参，一个出参
 *  指定入参，指定出参,入参类型和出参类型可以不一致,入参之间的类型也可以不一样
 */
public class BiFunctionTest {

    @Test
    void test1(){
        BiFunction<Integer, Long, String> fun1 = (x, y) -> x.toString() + y.toString();


        System.out.println(fun1.apply(12, 12L));

    }


}
