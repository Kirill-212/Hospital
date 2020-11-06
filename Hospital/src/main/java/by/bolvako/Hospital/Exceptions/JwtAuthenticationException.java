package by.bolvako.Hospital.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String message, Throwable t) {
        super( message, t);
    }
    public JwtAuthenticationException(String  message) {
        super( message);
    }
}
