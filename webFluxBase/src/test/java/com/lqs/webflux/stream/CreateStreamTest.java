package com.lqs.webflux.stream;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreateStreamTest {

    // 使用数组创建流
    @Test
    void test1(){

        int[] nums = {1,2,3,4,5};
        // 第一种方式
        IntStream intStream1 = IntStream.of(nums);
        // 第二种方式
        IntStream intStream2 = Arrays.stream(nums);


        System.out.println(intStream1.sum());
        System.out.println(intStream2.sum());

    }


    // 使用集合创建流
    @Test
    void test2(){

        List<String> names = new ArrayList<>();
        Stream<String> nameStream = names.stream();

        Set<String> cities = new HashSet<>();
        Stream<String> citiesSteam = cities.stream();
    }

    // 创建数字流
    @Test
    void test3(){

        // 创建一个包含1，2，3的stream
        IntStream intStream = IntStream.of(1, 2, 3);
        System.out.println(intStream.sum());

        // 创建一个某个范围的stream
        System.out.println(IntStream.range(1, 5).sum()); // [1,5)
        System.out.println(IntStream.rangeClosed(1, 5).sum()); // [1,5]


        // new Random().ints()创建一个无限流
        // limit(5)限制流中的个数最多为5个
        // ints(5)限制流的大小为5个
        // new Random().ints().limit(5) == new Random().ints(5)都是限制这个流的大小的
        System.out.println(new Random().ints().limit(1).sum());

        // peek和foreach差不多，可以对每一个元素进行操作,
        new Random().ints(5).peek(System.out::println).sum();

    }


    /**
     * 自定义流
     */
    @Test
    void test4(){
        Stream<Double> peek = Stream.generate(() -> Math.random()).limit(5).peek(System.out::println);
        System.out.println(peek.count());
    }




    public static void printA(int i)  {
        System.out.println(i);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void printB(int i)  {
        System.err.println(i);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 串行 有序
     * 串行和并行测试，默认是串行，效率低
     */
    @Test
    public void test5(){
        IntStream.rangeClosed(1,100)
                .peek(CreateStreamTest::printA)
                .count();
    }


    /**
     * 并行 无序
     * 串行和并行测试，默认是串行，效率低
     */
    @Test
    public void test6(){
        IntStream.rangeClosed(1,100)
                .parallel() // 开启并行
                .peek(CreateStreamTest::printA)
                .count();
    }



    /**
     * 并行 串行 混合处理 具体采用什么方式，取决于最后的方式
     * 串行和并行测试，默认是串行，效率低
     */
    @Test
    public void test7(){
        IntStream.rangeClosed(1,100)
                .parallel()  // 并行打开
                .peek(CreateStreamTest::printA)
                .sequential() // 关闭并行
                .peek(CreateStreamTest::printB)
                .count();
    }
}
