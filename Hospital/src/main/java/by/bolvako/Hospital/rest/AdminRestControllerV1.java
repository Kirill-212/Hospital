package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.Exceptions.UsersValidationException;
import by.bolvako.Hospital.dto.*;
import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.Status;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.*;
import by.bolvako.Hospital.service.DoctorService;
import by.bolvako.Hospital.service.PatientService;
import by.bolvako.Hospital.service.ReceptionService;
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
    private final ReceptionService receptionService;
    private final UserCrudRepository userCrudRepository;
    private final PatientCrudRepository patientCrudRepository;
    private final DoctorCrudRepository doctorCrudRepository;
    @Autowired
    public AdminRestControllerV1(PatientService patientService,ReceptionService receptionService,PatientCrudRepository patientCrudRepository,DoctorCrudRepository doctorCrudRepository,
                                 UserService userService,UserCrudRepository userCrudRepository,DoctorService doctorService) {
        this.userService = userService;
        this.userCrudRepository=userCrudRepository;
        this.doctorService=doctorService;
        this.patientService=patientService;
        this.patientCrudRepository=patientCrudRepository;
        this.doctorCrudRepository=doctorCrudRepository;
        this.receptionService=receptionService;
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
        List<User> users=userService.getAll();
        String json="{";
        for(int i=0 ;i<users.size();i++){
            json+="{"+'"'+"Role"+'"'+":"+'"'+userService.getAll().get(i).getRoles().get(0).getName()+'"'+","+'"'+"user"+'"'+":"+'"'+users.get(i).getEmail()+'"'+","+'"'+"firstName"+'"'+":"
                    +'"'+users.get(i).getFirstName()+'"'+","+'"'+"lastName"+'"'+":"+'"'+users.get(i).getLastName()+'"'
                    +","+'"'+"id_user"+'"'+":"+'"'+users.get(i).getId()+'"'+"},";
        }
        json+="}";
        json=json.replace("},}","}}");
        System.out.println(json);
        Map<Object, Object> response = new HashMap<>();
        response.put("users",json);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "patient")
    public ResponseEntity PostPatient(@RequestBody AdminUserDto requestDto) {
        List<Patient> patient=patientService.getAll();

        String json="{";
        for(int i=0 ;i<patient.size();i++){
            json+="{"+'"'+"home_adress"+'"'+":"+'"'+patient.get(i).getHomeadress()+'"'+","+'"'+"id"+'"'+":"+'"'+patient.get(i).getId()+'"'+","+'"'+"passport"+'"'+":"
                    +'"'+patient.get(i).getPassport()+'"'+","+'"'+"id_user"+'"'+":"+'"'+patient.get(i).getUser().getId()+'"'+"},";
        }
        json+="}";
        json=json.replace("},}","}}");
        System.out.println(json);
        Map<Object, Object> response = new HashMap<>();
        response.put("patients",json);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "doctor")
    public ResponseEntity PostDoctor(@RequestBody AdminUserDto requestDto) {
        List<Doctor> doctors=doctorService.getAll();

        String json="{";
        for(int i=0 ;i<doctors.size();i++){
            json+="{"+'"'+"Name_Hospital"+'"'+":"+'"'+doctors.get(i).getName_Hospital()+'"'+","+'"'+"id"+'"'+":"+'"'+doctors.get(i).getId()+'"'+","+'"'+"passport"+'"'+":"
                    +'"'+doctors.get(i).getPassport()+'"'+","+'"'+"id_user"+'"'+":"+'"'+doctors.get(i).getUser().getId()+'"'
                    +","+'"'+"Specialty"+'"'+":"+'"'+doctors.get(i).getSpecialty()+'"'+"},";
        }
        json+="}";
        json=json.replace("},}","}}");
        System.out.println(json);
        Map<Object, Object> response = new HashMap<>();
        response.put("doctor",json);
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
    public ResponseEntity DeleteUser(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();

        try{
            System.out.println(userService.findById(id));
            User user=userService.findById(id);

        if((user.toString().length()==0 )|| !patientService.GetUserInPatient(userService.findById(id))||
        !doctorService.GetUserInDoctor(userService.findById(id))||user.getRoles().toString().contains("ROLE_ADMIN")){

            response.put("userError", "user not found or delete on patient or doctor or this user ADMIN");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response.put("userError", "user del successfully");
        userService.delete(id);
        }catch (Exception e){
            response.put("userError", "user not found or delete on patient or doctor or this user ADMIN");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @RequestMapping(value = "delreception", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteRECEPTION(){
        Map<Object, Object> response = new HashMap<>();

        try{
            receptionService.delete();
            response.put("userError", "reception delete successfully");
        }catch (Exception e){
            response.put("userError", "error delete");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("addDoctor")
    public ResponseEntity RegiserDoctor(@Valid @RequestBody DoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try {
            Doctor doctor = requestDto.toDoctor();
            User user = userService.findById(doctor.getId());
            if(user.getRoles().toString().contains("ROLE_ADMIN")){
                response.put("userError", "user ADMIN this user cannot be added");
            }
          else  if (doctorService.GetUserInDoctor(user) && patientService.GetUserInPatient(user)) {
                user.setStatus(Status.ACTIVE);
                doctor.setUser(user);
                Doctor doctor1 = doctorService.register(doctor);
                System.out.println(doctor1.toString());
                response.put("userError", "user add successfully");
            } else {
                response.put("userError", "user not found or user include on doctor or patient");
            }

        }catch (Exception e){
            response.put("userError", "user not found or user include on doctor or patient");
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("delDoctor")
    public ResponseEntity DelDoctor(@Valid @RequestBody DeleteDoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try {
            Doctor doctor = requestDto.toDoctor();
            Doctor doctors = doctorService.findById(doctor.getId());
            User user = userService.findById(doctors.getUser().getId());

            if (user != null) {
                doctorService.delete(doctor.getId());
                response.put("userError", "user delete successfully");
            } else {
                response.put("userError", "user not found ");
            }
        }catch (Exception e){
            response.put("userError", "user not found ");
        }


        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("updateDoctor")
    public ResponseEntity UpdateDoctor(@Valid @RequestBody UpdateDoctorDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try{
        Doctor doctor=requestDto.toDoctor();
        Doctor doctors=doctorService.findById(doctor.getId());
        User user=userService.findById(doctors.getUser().getId());
            doctor.setUser(user);
            doctorCrudRepository.save(doctor);
            System.out.println("update прошло успешно");
            response.put("userError", "user update successfully");
        }catch (Exception e){
            response.put("userError", "user not found or user include on doctor or patient");
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updatePatient")
    public ResponseEntity UpdatePatient(@Valid @RequestBody UpdatePatientDto requestDto, BindingResult errors) {
    Map<Object, Object> response = new HashMap<>();

    if(errors.hasErrors()){
        throw new UsersValidationException(errors);
    }
    Patient patient=requestDto.toPatient();
    Patient patients=patientService.findById(patient.getId());
    try{
    User user=userService.findById(patients.getUser().getId());
    if(user != null){
            patient.setUser(user);
            patientCrudRepository.save(patient);
            System.out.println("Изменение прошло успешно");
            response.put("userError", "operation completed successfully");
            System.out.println(patients.toString()+"||Patient");
        }
        else{
            response.put("userError", "user not found or user include on doctor or patient");
        }
    }catch (Exception e){
        response.put("userError", "user not found or user include on doctor or patient");
    }


    return new ResponseEntity<>(response, HttpStatus.OK);
}
    @PostMapping("delPatient")
    public ResponseEntity DelPatient(@Valid @RequestBody DeletePatientDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try {
            Patient patient = requestDto.toPatient();
            Patient patients = patientService.findById(patient.getId());
            User user = userService.findById(patients.getUser().getId());

            patientService.delete(patient.getId());
            System.out.println("Удаление прошло успешно");
            response.put("userError", "user delete successfully");
        }catch (Exception e){
            response.put("userError", "user not found ");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("addPatient")
    public ResponseEntity RegiserPatient(@Valid @RequestBody PatientDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try {
            Patient patient = requestDto.toPatient();
            User user = userService.findById(patient.getId());

            if(user.getRoles().toString().contains("ROLE_ADMIN")){
                response.put("userError", "user ADMIN this user cannot be added");
            }
          else  if (doctorService.GetUserInDoctor(user) && patientService.GetUserInPatient(user)) {
                response.put("userError", "user add successfully");
                user.setStatus(Status.ACTIVE);
                patient.setUser(user);
                Patient patient1 = patientService.register(patient);
                System.out.println(patient1.toString());
            } else {
                response.put("userError", "user not found or user include on doctor or patient");
            }
        }
        catch (Exception e){
            response.put("userError", "user not found or user include on doctor or patient");
        }


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
