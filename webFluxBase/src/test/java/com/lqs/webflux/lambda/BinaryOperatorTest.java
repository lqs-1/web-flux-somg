package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;

/**
 * BinaryOperator内置函数式接口： 两个入参，一个出参
 *  指定入参类型，三个类型都一样
 */
public class BinaryOperatorTest {

    @Test
    void test1(){
        BinaryOperator<Integer> fun1 = (x, y) -> x + y;

        System.out.println(fun1.apply(12, 12));
    }

}
