package com.example.demo.controller.task;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.TasksApi;
import com.example.demo.model.PageDTO;
import com.example.demo.model.TaskDTO;
import com.example.demo.model.TaskForm;
import com.example.demo.model.TaskListDTO;
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

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset) {
        var entityList = TaskService.find(limit, offset);
        var dtoList = entityList.stream()
                .map(taskEntity -> {
                    var taskDTO = new TaskDTO();
                    taskDTO.setId(taskEntity.getId());
                    taskDTO.setTitle(taskEntity.getTitle());
                    return taskDTO;
                })
                .collect(Collectors.toList());
        var pageDTO = new PageDTO();
        pageDTO.setLimit(limit);
        pageDTO.setOffset(offset);
        pageDTO.setSize(dtoList.size());
        var dto = new TaskListDTO();
        dto.setPage(pageDTO);
        dto.setResults(dtoList);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> editTask(Long taskId, TaskForm taskForm) {
        var entity = TaskService.update(taskId, taskForm.getTitle());
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        TaskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }
}
