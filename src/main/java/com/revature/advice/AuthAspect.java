package com.revature.advice;

import com.revature.annotations.Authorized;
import com.revature.exceptions.NotLoggedInException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {

    // Spring will autowire a proxy object for the request
    // It isn't a request object itself, but if there is an active request
    // the proxy will pass method calls to the real request
    private final HttpServletRequest req;

    public AuthAspect(HttpServletRequest req) {
        this.req = req;
    }

    // This advice will execute around any method annotated with @Authorized
    // If the user is not logged in, an exception will be thrown and handled
    // Otherwise, the original method will be invoked as normal
    @Around("@annotation(authorized)")
    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

        HttpSession session = req.getSession(); // Get the session (or create one)

        // If the user is not logged in
        if(session.getAttribute("user") == null) {
            throw new NotLoggedInException("Must be logged in to perform this action");
        }

        return pjp.proceed(pjp.getArgs()); // Call the originally intended method
    }
}
