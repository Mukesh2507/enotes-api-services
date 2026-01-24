package in.mk.main.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoDto {
	
	
	private Integer id;

	private String title;

	private StatusDto status;

	
	 private Integer createdBy;
		
	
     private Date createdOn;
		
		
	 private Integer updatedBy;
		
	
	 private Date updatedOn;
	 
	 
	 
	 

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
	 public static class StatusDto{
		 private Integer id;
		 private String name;
	 }
	
	

}
