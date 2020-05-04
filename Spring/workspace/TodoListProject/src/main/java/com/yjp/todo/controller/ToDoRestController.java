package com.yjp.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yjp.todo.entity.Todo;
import com.yjp.todo.exception.ResourceNotFoundException;
import com.yjp.todo.repository.ToDoRepository;


@RestController
public class ToDoRestController {
	@Autowired
	private ToDoRepository repository;
	
	@GetMapping("/todos")
	public List<Todo> getTodos() {
		return repository.findAll();
	}
	
	@GetMapping("/todos/{id}")
	public Todo getTodo(@PathVariable Long id) {
		Optional<Todo> todoOpt = repository.findById(id);
		Todo todo = todoOpt.orElseThrow(() -> new ResourceNotFoundException("Todo", "Id", id));
		return todo;
	}
	
	@PostMapping("/todos")
	public Todo create(@RequestBody Todo todo) {
		return repository.save(todo);
	}
	
	@PutMapping("/todos/{id}")
	public Todo update(@PathVariable Long id, @RequestBody Todo todoedit) {
		Todo todo = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "Id", id));
		todo.setText(todoedit.getText());
		todo.setChecked(todoedit.getChecked());
		Todo updateTodo = repository.save(todo);
		return updateTodo;
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Todo todo = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "Id", id));
		repository.delete(todo);
		return new ResponseEntity<String>("id : " + todo.getId() + " deleted", HttpStatus.OK);
	}

}
