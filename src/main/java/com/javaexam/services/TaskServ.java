package com.javaexam.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javaexam.models.Task;
import com.javaexam.repositories.TaskRepo;

@Service
public class TaskServ {
    private final TaskRepo taskRepo;
    
    public TaskServ(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    public Task findOne(Long id) {
        Optional<Task> optional = taskRepo.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}