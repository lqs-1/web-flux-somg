package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;


/**
 * Predicate内置函数式接口： 一个入参，一个出参
 *  输入一个值判断这个值，然后返回boolean类型
 *  还可以进行与或非的计算
 *  这个接口还有自己的具体子接口可以使用
 */
public class PredicateTest {


    @Test
    void test1(){
        Predicate<Integer> pre1 = (x) -> x > 8;

        Predicate<Integer> pre2 = x -> x > 10;

        Predicate<String> pre3 = x -> x.contains("123");


        System.out.println(pre1.test(10));
        System.out.println(pre2.test(10));
        System.out.println(pre3.test("12345"));



        System.out.println(pre1.and(pre2).test(10));
        System.out.println(pre1.or(pre2).test(10));
        System.out.println(pre1.negate().test(10));

    }


    @Test
    void test2(){
        IntPredicate intPre = x -> x > 8;
        DoublePredicate doublePre = x -> x > 10;


        System.out.println(intPre.test(1));
        System.out.println(doublePre.test(1));

    }



}
