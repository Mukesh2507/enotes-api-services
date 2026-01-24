package in.mk.main.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.mk.main.dto.TodoDto;
import in.mk.main.dto.TodoDto.StatusDto;
import in.mk.main.entity.Todo;
import in.mk.main.enums.TodoStatus;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.respository.TodoRepository;
import in.mk.main.util.CommonUtil;
import in.mk.main.util.Validation;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		
		//validate todo status
		
		  validation.todoValidation(todoDto);
		
		Todo todo=mapper.map(todoDto,Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo save=todoRepository.save(todo);
		
		if (!ObjectUtils.isEmpty(save)) {
			
			return true;
			
		}
		return false;
	}
	
	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		Todo todo= todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found !id invalid"));
		
	TodoDto todoDto	=mapper.map(todo, TodoDto.class);
		setStatus(todoDto,todo);
	return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		for(TodoStatus st :TodoStatus.values()) {
			if (st.getId().equals(todo.getStatusId())) {
				
				StatusDto statusDto =StatusDto.builder()
						             .id(st.getId())
						             .name(st.getName())
						             .build();
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByUser() {
	
		Integer userId=CommonUtil.getLoggedInUSer().getId();
		
		List<Todo> todos=todoRepository.findByCreatedBy(userId);
		
		 return todos.stream().map(td->mapper.map(td, TodoDto.class)).toList();
		
		
	
	}

	

}
