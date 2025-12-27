package com.example.demo.controller.task;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.TasksApi;
import com.example.demo.model.TaskDTO;
import com.example.demo.model.TaskForm;
import com.example.demo.service.task.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService TaskService;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        var entity = TaskService.find(taskId);
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        var entity = TaskService.create(form.getTitle());
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity
                .created(URI.create("/tasks/" + dto.getId()))
                .body(dto);
    }
}
