package com.example.producer.repository;

import com.example.producer.model.Task;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

  Task findFirstByOrderByIdAsc();

  Task findFirstByOrderByIdDesc();

}
