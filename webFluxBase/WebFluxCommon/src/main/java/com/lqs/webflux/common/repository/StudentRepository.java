package com.lqs.webflux.common.repository;

import com.lqs.webflux.common.domian.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

/**
 * @author 李奇凇
 * @moduleName StudentRepository
 * @date 2022/11/18 下午2:55
 * @do 学生服务层接口
 */

// 第一个泛型为要操作的对象类型，第二个泛型表示要操作对象的id类型
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
}
