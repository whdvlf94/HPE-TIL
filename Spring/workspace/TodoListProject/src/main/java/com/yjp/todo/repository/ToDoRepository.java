package com.yjp.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yjp.todo.entity.Todo;

public interface ToDoRepository extends JpaRepository<Todo, Long>{

}
