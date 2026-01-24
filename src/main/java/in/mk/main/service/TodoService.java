package in.mk.main.service;

import java.util.List;

import in.mk.main.dto.TodoDto;

public interface TodoService {

	public Boolean saveTodo(TodoDto todoDto) throws Exception;
	
	public TodoDto getTodoById(Integer id) throws Exception;
	
	public List<TodoDto> getTodoByUser();
	
	
	
}
