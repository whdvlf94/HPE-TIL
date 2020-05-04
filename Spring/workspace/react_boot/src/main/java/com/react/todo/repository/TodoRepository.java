package com.react.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.react.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

}
