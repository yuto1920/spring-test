package com.example.demo.repository.task;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TaskRepository {
    @Select("SELECT id, title FROM tasks WHERE id = #{taskId}")
    Optional<TaskRecord> select(long taskId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tasks (title) VALUES (#{title})")
    void insert(TaskRecord record);
}
