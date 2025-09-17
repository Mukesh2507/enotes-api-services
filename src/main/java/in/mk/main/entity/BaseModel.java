package in.mk.main.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass  
public abstract class BaseModel {
	
	
	private Boolean isActive;
	private Boolean isDeleted;;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;


}
