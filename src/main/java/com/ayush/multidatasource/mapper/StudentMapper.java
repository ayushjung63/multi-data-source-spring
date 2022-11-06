package com.ayush.multidatasource.mapper;

import com.ayush.multidatasource.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
    })
    List<Student> findAll();
}
