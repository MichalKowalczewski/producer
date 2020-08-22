package com.example.producer.service;

import com.example.producer.model.DTO.TaskDTO;

import java.util.List;

public interface TaskService {

  List<TaskDTO> findAll();

  TaskDTO create(TaskDTO task);

  TaskDTO update(Integer id, TaskDTO task);

  void delete(Integer id);

  TaskDTO findById(Integer id);

  boolean checkIfTableIsEmpty();

  TaskDTO findLast();

}
