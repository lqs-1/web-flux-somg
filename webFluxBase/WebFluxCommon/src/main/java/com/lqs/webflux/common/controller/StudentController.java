package com.lqs.webflux.common.controller;

import com.lqs.webflux.common.domian.Student;
import com.lqs.webflux.common.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName StudentController
 * @date 2022/11/18 下午2:54
 * @do 学生控制器
 */

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentRepository repository;

    // 一次性返回所有数据
    @GetMapping("all")
    public Flux<Student> getAllOne(){
        return repository.findAll();
    }



    // sse返回数据
    @GetMapping(value = "sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllTwo(){
        return repository.findAll();
    }




}
