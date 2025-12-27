package com.example.demo.service.task;

import org.springframework.stereotype.Service;

import com.example.demo.repository.task.TaskRecord;
import com.example.demo.repository.task.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskEntity find(long taskId) {
        return taskRepository.select(taskId)
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .orElseThrow(() -> new TaskEntityNotFoundException(taskId));

    }

    public TaskEntity create(String title) {
        var record = new TaskRecord(null, title);
        taskRepository.insert(record);
        return new TaskEntity(record.getId(), record.getTitle());
    }

}
