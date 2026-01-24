package in.mk.main.dto;

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
public class Emailrequest {
	
	private String to;
	
	private String subject;
	
	private String title;
	
	private String message;
	

}
