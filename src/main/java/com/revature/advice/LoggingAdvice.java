package com.revature.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAdvice {
	
	Logger log = LoggerFactory.getLogger(LoggingAdvice.class);
	
	@Pointcut(value="execution(* com.revature.*.*.*(..))")
	public void pointCut() {
	}
	
	@Before("pointCut()")
	public Object appLogger(JoinPoint pjp) {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] methodArgs = pjp.getArgs();
		Object o = null;
		
		try {
			log.info(String.format("method invoked %1$s : %2$s () : arguments : %3$s", className, methodName, mapper.writeValueAsString(methodArgs)));
		} catch (Throwable e) {
			log.error("logger unable to parse methodArgs");
		}
		
		return o;
	}
}
