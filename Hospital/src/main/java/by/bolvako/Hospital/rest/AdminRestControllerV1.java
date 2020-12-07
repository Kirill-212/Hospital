package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.Exceptions.UsersValidationException;
import by.bolvako.Hospital.dto.*;
import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.*;
import by.bolvako.Hospital.service.DoctorService;
import by.bolvako.Hospital.service.PatientService;
import by.bolvako.Hospital.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final UserCrudRepository userCrudRepository;
    private final PatientCrudRepository patientCrudRepository;
    private final DoctorCrudRepository doctorCrudRepository;
    @Autowired
    public AdminRestControllerV1(PatientService patientService,PatientCrudRepository patientCrudRepository,DoctorCrudRepository doctorCrudRepository,
                                 UserService userService,UserCrudRepository userCrudRepository,DoctorService doctorService) {
        this.userService = userService;
        this.userCrudRepository=userCrudRepository;
        this.doctorService=doctorService;
        this.patientService=patientService;
        this.patientCrudRepository=patientCrudRepository;
        this.doctorCrudRepository=doctorCrudRepository;
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
    @PostMapping(value = "admin")
    public ResponseEntity Post(@RequestBody AdminUserDto requestDto) {

        System.out.println("-------------------");

        System.out.println("-------------------");
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//

//        AdminUserDto result = AdminUserDto.fromUser(user);
        List<User> users=userService.getAll();
        String json="{";
        for(int i=0 ;i<users.size();i++){
            json+="{"+'"'+"Role"+'"'+":"+'"'+userService.getAll().get(i).getRoles().get(0).getName()+'"'+","+'"'+"user"+'"'+":"+'"'+users.get(i).getEmail()+'"'+","+'"'+"firstName"+'"'+":"
                    +'"'+users.get(i).getFirstName()+'"'+","+'"'+"lastName"+'"'+":"+'"'+users.get(i).getLastName()+'"'+"},";
        }
        json+="}";
        json=json.replace("},}","}}");
        System.out.println(json);
//        JSONObject jsonObj = new JSONObject(userService.getAll().toString());
//        System.out.println(jsonObj);
//        System.out.println(userService.getAll().listIterator());
//        System.out.println(userService.getAll().toString());
//        System.out.println(userService.getAll());
        Map<Object, Object> response = new HashMap<>();
        response.put("users",json);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "admin")
    public String Get( Model model,
                      @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> page;

                page=userCrudRepository.findAll(pageable);

     //   System.out.println("-------------------");

      //  System.out.println("-------------------");
        model.addAttribute("page",page);
        Map<Object, Object> response = new HashMap<>();
        return "Admin";
    }

    @RequestMapping(value = "delUser/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> DeleteUser(@PathVariable(value = "id") Long id){
        if(userService.findById(id) == null ||patientService.GetUserInPatient(userService.findById(id))||
        doctorService.GetUserInDoctor(userService.findById(id))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);

        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }


    @PostMapping("addDoctor")
    public ResponseEntity RegiserDoctor(@Valid @RequestBody DoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        Doctor doctor=requestDto.toDoctor();
        User user=userService.findById(doctor.getId());
        System.out.println(doctorService.GetUserInDoctor(user));
        System.out.println(patientService.GetUserInPatient(user));
        if(user!=null && doctorService.GetUserInDoctor(user) && patientService.GetUserInPatient(user)){
            doctor.setUser(user);
          Doctor doctor1=  doctorService.register(doctor);
            System.out.println(doctor1.toString());
        }
        else{
            response.put("userError", "user not found or user found in table");
        }
        response.put("username", "username");


        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }
    @PostMapping("delDoctor")
    public ResponseEntity DelDoctor(@Valid @RequestBody DeleteDoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        Doctor doctor=requestDto.toDoctor();
        Doctor doctors=doctorService.findById(doctor.getId());
        User user=userService.findById(doctors.getUser().getId());
        System.out.println(doctors.toString()+"||Doctor");
        System.out.println(user.toString()+"||USers");
        if(user!=null  ){
            doctorService.delete(doctor.getId());
            userService.delete(user.getId());
            System.out.println("Удаление прошло успешно");
        }
        else{
            response.put("userError", "user not found or user found in table");
        }
        response.put("username", "username");


        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }
    @PostMapping("updateDoctor")
    public ResponseEntity UpdateDoctor(@Valid @RequestBody UpdateDoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        Doctor doctor=requestDto.toDoctor();
        Doctor doctors=doctorService.findById(doctor.getId());
        User user=userService.findById(doctors.getUser().getId());
        System.out.println(doctors.toString()+"||Doctor");
        System.out.println(user.toString()+"||USers");
        if(user!=null  && doctors!=null){
                doctor.setUser(user);
                doctorCrudRepository.save(doctor);
            System.out.println("Удаление прошло успешно");
        }
        else{
            response.put("userError", "user not found or user found in table");
        }
        response.put("username", "username");


        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }
//https://habr.com/ru/post/479286/
@PostMapping("updatePatient")
public ResponseEntity UpdatePatient(@Valid @RequestBody UpdatePatientDto requestDto, BindingResult errors) {
    Map<Object, Object> response = new HashMap<>();

    if(errors.hasErrors()){
        throw new UsersValidationException(errors);
    }
    Patient patient=requestDto.toPatient();
    Patient patients=patientService.findById(patient.getId());
    User user=userService.findById(patients.getUser().getId());
    System.out.println(patients.toString()+"||Patient");
    if(patients!=null  && user!=null){
        patient.setUser(user);
        patientCrudRepository.save(patient);
        System.out.println("Изменение прошло успешно");
    }
    else{
        response.put("userError", "user not found or user found in table");
    }
    response.put("username", "username");


    return new ResponseEntity<>(patient, HttpStatus.CREATED);
}
    @PostMapping("delPatient")
    public ResponseEntity DelPatient(@Valid @RequestBody DeletePatientDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        Patient patient=requestDto.toPatient();
        Patient patients=patientService.findById(patient.getId());
        User user=userService.findById(patients.getUser().getId());
        System.out.println(patients.toString()+"||Doctor");
        System.out.println(user.toString()+"||USers");
        if(user!=null  ){
           patientService.delete(patient.getId());
            userService.delete(user.getId());
            System.out.println("Удаление прошло успешно");
        }
        else{
            response.put("userError", "user not found or user found in table");
        }
        response.put("username", "username");


        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PostMapping("addPatient")
    public ResponseEntity RegiserPatient(@Valid @RequestBody PatientDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        Patient patient=requestDto.toPatient();
        User user=userService.findById(patient.getId());
        System.out.println(doctorService.GetUserInDoctor(user));
        System.out.println(patientService.GetUserInPatient(user));
        if(user!=null && doctorService.GetUserInDoctor(user) && patientService.GetUserInPatient(user)){
            patient.setUser(user);
            Patient patient1=  patientService.register(patient);
            System.out.println(patient1.toString());
        }
        else{
            response.put("userError", "user not found or user found in table");
        }
        response.put("username", "username");


        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }
}
