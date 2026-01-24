package in.mk.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {
	
	
	
	private List<NotesDto> notesDtos;
	private Integer pageNo;
	private Integer pageSize;
	private Integer totalElement;
	private Integer totalPages;
	private Boolean isFirst;
	private Boolean isLast;
	

}
