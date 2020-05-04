package com.yjp.todo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yjp.todo.entity.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoRepositoryTest {
	
	@Autowired
	ToDoRepository todorepository;
	
	@Test
	public void todo() throws Exception {
		Todo todo = new Todo();
		todo.setText("todo2");
		todo.setChecked(true);
		
		Todo saveTodo = todorepository.save(todo);
		System.out.println(saveTodo);
		
	}

}
