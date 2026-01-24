package in.mk.main.config.security;

import java.io.IOException;
import java.net.http.HttpResponse.BodyHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.mk.main.dto.handler.GenericResponse;
import in.mk.main.respository.FavouriteNotesRepository;
import in.mk.main.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
    private final FavouriteNotesRepository favouriteNotesRepository;

    JwtFilter(FavouriteNotesRepository favouriteNotesRepository) {
        this.favouriteNotesRepository = favouriteNotesRepository;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		  String authHeader=request.getHeader("Authorization");
		  
		  //authorization =Bearer gfgdgfgfggffgg
		  String token =null;
		  String username =null;
		  if(authHeader!=null && authHeader.startsWith("Bearer")) {
			  
			   token=authHeader.substring(7);
			  username= jwtService.extractUsername(token);
		  }
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
	        	UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(username);	
			  Boolean validateToken=jwtService.validateToken(token, userDetails);
			
			  if(validateToken) {
			       UsernamePasswordAuthenticationToken authenticationToken	= new UsernamePasswordAuthenticationToken (userDetails,null,userDetails.getAuthorities());
			       
			         authenticationToken.setDetails(new WebAuthenticationDetailsSource()
			        		 .buildDetails(request));
			         
			         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			         
			         
			  }
		}
		}
		catch (Exception e) {
			
			generateResponseError(response,e);
			return;
		}
		filterChain.doFilter(request, response);
		}

	private void generateResponseError(HttpServletResponse response,Exception e) throws  IOException {
		response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Object error=GenericResponse.builder()
		.status("failed")
		.message(e.getMessage())
		.responseStatus(HttpStatus.UNAUTHORIZED)
		.build().create().getBody();
		response.getWriter().write(new ObjectMapper().writeValueAsString(error));
		
	}
	
}
