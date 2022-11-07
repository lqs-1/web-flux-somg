package com.lqs.webflux.lambda.domain;

import lombok.Data;

@Data
public class Person {

    private String userName;


    public Person() {}

    public Person(String userName) {
        this.userName = userName;
    }

    public static void sleep(Integer time){
        System.out.println("休息" + time +"分钟");
    }

    public void eat(Integer time){
        System.out.println("吃饭" + time +"分钟");
    }

    public String play(Integer time){
        return "玩" + time +"分钟";
    }

    public String haha(Integer time, String playName){
        return playName + time +"分钟";
    }






}
