package in.mk.main.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
//	@Before("execution(*in.mk.main.controller..*(..))")
//	public void beforeController(JoinPoint JoinPoint) {
//		
//	Signature signature=JoinPoint.getSignature();
//	String className=signature.getDeclaringType().getSimpleName();
//	String methodName=signature.getName();
//	log.info("Calling :: {} :: {} ()",className,methodName);
//		
//}
//	
//	@After("excution(*in.mk.main.controller..*(..))")
//	public void AfterController(JoinPoint JoinPoint) {
//		
//	Signature signature=JoinPoint.getSignature();
//	String className=signature.getDeclaringType().getSimpleName();
//	String methodName=signature.getName();
//	log.info("End Calling :: {} :: {} ()",className,methodName);
//		
//}
	
	@Around("excution(*in.mk.main.controller..*(..))")
	public Object JointPointController(ProceedingJoinPoint JoinPoint) throws Throwable {
		
	Signature signature=JoinPoint.getSignature();
	String className=signature.getDeclaringType().getSimpleName();
	String methodName=signature.getName();
	log.info("startCalling :: {} :: {} ()",className,methodName);
	long start = System.currentTimeMillis();

	Object result=JoinPoint.proceed();
	long duration = System.currentTimeMillis()-start;
	log.info("End Calling :: {} :: {} ():: {}  ms",className,methodName,duration);

	return result;	
}
	
	@Around("excution(*in.mk.main.service..*(..))")
	public Object JointPointService(ProceedingJoinPoint JoinPoint) throws Throwable {
		
	Signature signature=JoinPoint.getSignature();
	String className=signature.getDeclaringType().getSimpleName();
	String methodName=signature.getName();
	log.info("startCalling :: {} :: {} ()",className,methodName);
	long start = System.currentTimeMillis();

	Object result=JoinPoint.proceed();
	long duration = System.currentTimeMillis()-start;
	log.info("End Calling :: {} :: {} ():: {}  ms",className,methodName,duration);

	return result;	
}
	
	
}
