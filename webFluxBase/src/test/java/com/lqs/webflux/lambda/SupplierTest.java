package com.lqs.webflux.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

/**
 * Supplier内置函数式接口： 没有入参，一个出参
 *  没有参数，生成一个结果
 */
public class SupplierTest {

    @Test
    void test1(){
        Supplier<String> supp1 = () -> "hahah";
        Supplier<Integer> supp2 = () -> 2+2;

        System.out.println(supp1.get());
        System.out.println(supp2.get());

    }

}
