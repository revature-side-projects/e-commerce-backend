	package com.revature.advice;

import com.revature.annotations.AuthRestriction;
import com.revature.annotations.Authorized;
import com.revature.annotations.AuthorizedAdmin;
import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;

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
    /**
     * 
    // This advice will execute around any method annotated with @Authorized
    // If the user is not logged in, an exception will be thrown and handled
    // Otherwise, the original method will be invoked as normal.
    // In order to expand upon this, you just need to add additional
    // values to the AuthRestriction enum.
    // Examples might be "Manager" or "Customer" along with suitable Role
    // values in the User class.
    // Then this method can be expanded to throw exceptions if the user does
    // not have the matching role.
    // Example:
    // User loggedInUser = (User) session.getAttribute("user");
    // Role userRole = loggedInUser.getRole();
    // if(authorized.value().equals(AuthRestriction.Manager) && !Role.Manager.equals(userRole)) {
    //     throw new InvalidRoleException("Must be logged in as a Manager to perform this action");
    // }
    // Then the RestExceptionHandler class can be expanded to include
    // @ExceptionHandler(InvalidRoleException.class)
    // which should return a 403 Forbidden such as:
    // @String errorMessage = "Missing required role to perform this action";
    // @return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
     */
    @Around("@annotation(authorized)")
    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

        HttpSession session = req.getSession(); // Get the session (or create one)

        // If the user is not logged in
        if(session.getAttribute("user") == null) {
            throw new NotLoggedInException("Must be logged in to perform this action");
        }
        
       

        return pjp.proceed(pjp.getArgs()); // Call the originally intended method
    }
    // This advice will execute around any method annotated with @AuthorizedAdmin
    // It will check the role of user, if role of user is not ADMIN, an an exception 
    // will be thrown and handled. Otherwise, the original method will be invoked as normal.
    @Around("@annotation(authorized)")
    public Object authenticateAdmin(ProceedingJoinPoint pjp, AuthorizedAdmin authorized) throws Throwable {

        HttpSession session = req.getSession(); // Get the session (or create one)


        
        User loggedInUser = (User) session.getAttribute("user"); // get the user from session
        String userRole = loggedInUser.getRole(); // get the role of user
        // check the role of user
        if(authorized.value().equals(AuthRestriction.ADMIN) && !"ADMIN".equals(userRole)) {
        	throw new InvalidRoleException("Must be logged in as a Admin to perform this action");
        }

        return pjp.proceed(pjp.getArgs()); // Call the originally intended method
    }
}
