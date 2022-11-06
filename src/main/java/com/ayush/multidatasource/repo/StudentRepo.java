package com.ayush.multidatasource.repo;

import com.ayush.multidatasource.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo  extends JpaRepository<Student,Long> {
}
