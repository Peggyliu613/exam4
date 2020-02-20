package com.javaexam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javaexam.models.Task;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long>{
    List<Task> findAll();
    List<Task> findByOrderByPriorityAsc();
    List<Task> findByOrderByPriorityDesc();
}