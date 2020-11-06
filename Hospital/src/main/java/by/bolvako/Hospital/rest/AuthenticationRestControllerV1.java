package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.Exceptions.UsersValidationException;
import by.bolvako.Hospital.dto.AuthenticationRequestDto;
import by.bolvako.Hospital.dto.UserDto;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.security.jwt.JwtTokenProvider;
import by.bolvako.Hospital.service.UserService;
import by.bolvako.Hospital.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
   private final EmailValidator emailValidator;
    private final UserService userService;
    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider, EmailValidator emailValidator,
                                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailValidator = emailValidator;
        this.userService = userService;
    }



    @PostMapping("Register")
    public ResponseEntity Regiser(@Valid @RequestBody UserDto requestDto, BindingResult errors) {
        emailValidator.validate(requestDto,errors);
//        if(errors.hasErrors()){
//            System.out.println(errors.getAllErrors());
//            System.out.println(errors.getTarget());
//            //System.out.println(errors.);
//            throw  new RuntimeException();
//        }

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        User user=requestDto.toUser();
        userService.register(user);


        Map<Object, Object> response = new HashMap<>();
        response.put("username", "username");


        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getEmail();
        System.out.println(username+'|'+requestDto.getPassword()+'|'+requestDto.toString());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = userService.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        String token = jwtTokenProvider.createToken(username, user.getRoles());
        System.out.println(token);
        Map<Object, Object> response = new HashMap<>();
        response.put("email", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
