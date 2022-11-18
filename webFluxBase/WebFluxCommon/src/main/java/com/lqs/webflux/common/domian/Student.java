package com.lqs.webflux.common.domian;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李奇凇
 * @moduleName Student
 * @date 2022/11/18 下午2:51
 * @do 学生实体类
 */

@Data
// 对应mongodb中生成的表名字
@Document(collection = "t_student")
public class Student {

    // mongodb中的id一般是字符串
    @Id
    private String id;

    private String name;

    private int age;

}
