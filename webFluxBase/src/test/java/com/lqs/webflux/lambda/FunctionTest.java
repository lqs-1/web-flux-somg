package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Function内置函数式接口： 一个入参，一个出参
 *  指定入参，指定出参，两者类型可以不一样
 *  这个接口还有自己的具体子接口可以使用
 */
public class FunctionTest {

    @Test
    void test1(){
        Function<Integer, String> fun1 = x -> x.toString();


        System.out.println(fun1.apply(12));
    }

    @Test
    void test2(){
        Function<Integer, Integer> fun1 = x -> x+1;
        Function<Integer, Integer> fun2 = x -> x*3;


        // IntFunction<Integer> fun1 = x -> x+1;
        // IntFunction<Integer>  fun2 = x -> x*3;


        // 先将12作为fun1的参数，计算结果为12，再将fun1的结果作为fun2的参数
        System.out.println(fun1.andThen(fun2).apply(12));
    }

}
