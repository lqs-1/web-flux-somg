package com.lqs.sse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author 李奇凇
 * @moduleName SomeController
 * @date 2022/11/17 下午1:59
 * @do sse测试
 */

@Controller
public class SomeController {


    @RequestMapping("defaultsse")
    public String defaultsse(){
        return "defaultsse";
    }


    // 向客户端发送普通响应
    @RequestMapping("common")
    public void commonHandle(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        for (int i = 0; i < 10; i++){
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // 向客户端发送默认事件的sse响应
    @RequestMapping(value = "sse/def")
    public void sseDefHandle(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        for (int i = 0; i < 10; i++){
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    // 向客户端发送自定义事件的sse响应
    @RequestMapping(value = "sse/custom")
    public void sseCustomHandle(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        for (int i = 0; i < 10; i++){
            // 设置自定义事件名称
            out.print("event:china\n");
            out.print("data:" + i + "\n");
            out.print("\r\n");
            out.flush();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
