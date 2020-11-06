package by.bolvako.Hospital.validator;

import by.bolvako.Hospital.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Validator
{
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\." +
                    "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
                    "(\\.[A-Za-z]{2,})$";

    private Pattern pattern;

    public EmailValidator()
    {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return  UserDto.class.equals(aClass);
    }

    public void validate(Object command, Errors errors){
        UserDto userDto=(UserDto)command;
        boolean result=userDto.getEmail().matches(EMAIL_PATTERN);
        if(!result){
            errors.reject("Error email not valid");
        }

    }

}
