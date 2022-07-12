package com.revature.exceptions;

public class TokenParseException extends AuthenticationException {

    public TokenParseException() { super("The provided token could not be parsed"); }

    public TokenParseException(Throwable cause) {
        super("The provided token could not be parsed", cause);
    }

    public TokenParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
