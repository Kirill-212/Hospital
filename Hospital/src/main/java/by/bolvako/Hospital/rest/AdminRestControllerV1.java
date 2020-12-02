package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.dto.AdminUserDto;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {

        System.out.println("-------------------");
        User user = userService.findById(id);
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getRoles());
        System.out.println("-------------------");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "page")
    public ResponseEntity<AdminUserDto> gete(@PathVariable(name = "id") Long id) {

        System.out.println("-------------------");
        User user = userService.findById(id);
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getRoles());
        System.out.println("-------------------");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
