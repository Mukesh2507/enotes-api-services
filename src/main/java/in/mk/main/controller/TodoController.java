package in.mk.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.TodoDto;
import in.mk.main.endpoint.TodoControllerEndpoint;
import in.mk.main.service.TodoService;
import in.mk.main.util.CommonUtil;

@RestController

public class TodoController implements TodoControllerEndpoint{
	
	
	
	@Autowired
	private TodoService todoService;
	
	
	@Override
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception{
		
		Boolean saveTodo=todoService.saveTodo(todo);
		
		if (saveTodo) {
			return CommonUtil.createBuildResponseMessage("Todo saved success", HttpStatus.CREATED);
		}else {
			
			return CommonUtil.createErrorResponseMessage("Todo not save", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@Override
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception{
		
		TodoDto todoDto =todoService.getTodoById(id);
		return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
		
		
	}
	
     @Override
	public ResponseEntity<?> getAllTodoByUser(@PathVariable Integer id) throws Exception{
		
		List<TodoDto> todoList =todoService.getTodoByUser();
		
		if (CollectionUtils.isEmpty(todoList)) {
			
			return ResponseEntity.noContent().build();
			
		}
		
		return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
		
		
		
		
		
	}
	
	{
		
		
	}
	

}
