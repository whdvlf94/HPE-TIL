package com.react.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.react.todo.entity.Todo;
import com.react.todo.exception.ResourceNotFoundException;
import com.react.todo.repository.TodoRepository;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TodoRestController {
	@Autowired
	private TodoRepository repository;
	
	@GetMapping("/todos")
	public List<Todo>  getTodos() {
		return repository.findAll();
	}
	
	@GetMapping("/todos/{id}")
	public Todo getTodo(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
	}
	
	@PostMapping("/todos")
	public List<Todo> create(@RequestBody Todo todo) {
		repository.save(todo);
		return repository.findAll();
	}
	
	@PutMapping("/todos/{id}")
	public List<Todo> update(@PathVariable Long id, @RequestBody Todo todoDetail) {

	    Todo todo = repository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));

	    todo.setChecked(todoDetail.getChecked());
	    todo.setText(todoDetail.getText());
	    repository.save(todo);
	    
	    return repository.findAll();
	}
	
	@DeleteMapping("/todos/{id}")
	public List<Todo> delete(@PathVariable Long id) {
	    Todo todo = repository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
	    repository.delete(todo);
	    return repository.findAll();
	}
	
	
}
