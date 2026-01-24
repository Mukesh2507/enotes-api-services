package in.mk.main.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import in.mk.main.entity.User;
import in.mk.main.util.CommonUtil;

@Component("auditAware")
public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		
	User loggedInUser=CommonUtil.getLoggedInUSer();	
		return Optional.of(loggedInUser.getId());
	}
	
	

}
