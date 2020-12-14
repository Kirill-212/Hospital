package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.Exceptions.UsersValidationException;
import by.bolvako.Hospital.dto.*;
import by.bolvako.Hospital.model.*;
import by.bolvako.Hospital.repository.ReceptionCrudRepository;
import by.bolvako.Hospital.service.DoctorService;
import by.bolvako.Hospital.service.PatientService;
import by.bolvako.Hospital.service.ReceptionService;
import by.bolvako.Hospital.service.UserService;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(UserRestControllerV1.class);
    private final UserService userService;
    private  final DoctorService doctorService;
    private  final PatientService patientService;
    private  final ReceptionService receptionService;
    private  final ReceptionCrudRepository receptionCrudRepository;
    @Autowired
    public UserRestControllerV1(UserService userService
    ,ReceptionCrudRepository receptionCrudRepository,DoctorService doctorService,PatientService patientService,ReceptionService receptionService) {
        this.doctorService=doctorService;
        this.userService = userService;
        this.patientService=patientService;
        this.receptionService=receptionService;
        this.receptionCrudRepository=receptionCrudRepository;
    }

//    @GetMapping(value = "{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
//        System.out.println("-------user------------");
//        User user = userService.findById(id);
//        System.out.println(user.getId());
//        System.out.println(user.getEmail());
//        System.out.println(user.getRoles());
//        System.out.println("-------------------");
//
//        if(user == null){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        UserDto result = UserDto.fromUser(user);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
    @PostMapping("addReception")
    public ResponseEntity RegiserReception(@Valid @RequestBody ReceptionDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();
        log.info("RegiserReception:/api/v1/users/addReception");
        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try{
            Reception reception=new Reception();
            Doctor doctor=doctorService.findById(requestDto.getId_doctor());
            Patient patient=patientService.findById(requestDto.getId_patient());
            reception.setDoctor(doctor);
            reception.setPatient(patient);
            reception.setTime(requestDto.getTime());
            reception.setComments("---");
            reception.setSymptoms(requestDto.getSymptoms());
            reception.setDiagnosis("---");
            System.out.println( receptionService.CheckDate(requestDto.getDate()));
            if(receptionService.CheckDate(requestDto.getDate()) && doctor!=null && patient!=null){
                reception.setDate_reception(requestDto.getDate());
                receptionService.Add(reception);
                response.put("userError", "reception add successfully");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else{
                response.put("userError", "error added check input data ");
            }
        }catch (Exception e){
            response.put("userError", "check input data or user is not active");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @PostMapping("allDoctor")
    public ResponseEntity PostDoctor( @Valid @RequestBody AdminUserDto requestDto, BindingResult errors) {
        List<Doctor> doctors=doctorService.getAll();

        StringBuilder json= new StringBuilder("[");
        for (Doctor doctor : doctors) {
            json.append("{" + '"' + "Name_Hospital" + '"' + ":" + '"').
                    append(doctor.getName_Hospital()).append('"').append(",").
                    append('"').append("id").append('"').append(":").append('"').
                    append(doctor.getId()).append('"').append(",").append('"').
                    append("Specialty").append('"').append(":").append('"').
                    append(doctor.getSpecialty()).append('"').append(",").append('"').
                    append("F").append('"').append(":").append('"').append(doctor.getUser().
                    getFirstName()).append('"').append(",").append('"').append("L").append('"').
                    append(":").append('"').append(doctor.getUser().getLastName()).append('"').append("},");
        }
        json.append("]");
        json = new StringBuilder(json.toString().replace("},]", "}]"));
        System.out.println(json);
        Map<Object, Object> response = new HashMap<>();
        response.put("doctor", json.toString());
        log.info("PostDoctor:/api/v1/users/allDoctor");
        return ResponseEntity.ok(response);
    }






    @RequestMapping(value = "patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetAllReceptionForPatient(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();
        log.info("GetAllReceptionForPatient:/api/v1/users/patient/{id}");
        StringBuilder json= new StringBuilder("[");
        try {
            List<Reception> rec = receptionService.getByIdPatient(patientService.findById(id));

            for (Reception reception : rec) {
                System.out.println(reception.toString());
                if(reception.getDoctor()!=null){
                json.append("{" + '"' + "comments" + '"' + ":" + '"').append(reception.getComments()).append('"').append(",")
                        .append('"').append("date")
                        .append('"').append(":").append('"').append(reception.getDate_reception()).append('"').append(",")
                        .append('"').append("diagnosis")
                        .append('"').append(":").append('"').append(reception.getDiagnosis()).append('"')
                        .append(",").append('"').append("F_L_patient").append('"')
                        .append(":").append('"').append(reception.getPatient().getUser().getLastName()).append(" ")
                        .append(reception.getPatient().getUser()
                        .getFirstName()).append('"').append(",").append('"').append("time_").append('"').append(":")
                        .append('"').append(reception.getTime())
                        .append('"').append(",")
                        .append('"').append("Doctor").append('"').append(":").append('"')
                        .append(reception.getDoctor().getUser().getLastName()).append(" ").
                        append(reception.getDoctor().getUser()
                        .getFirstName())
                        .append('"').append(",")
                        .append('"').append("Name_Hospital").append('"').append(":").append('"')
                        .append(reception.getDoctor().getName_Hospital())
                        .append('"').append(",")
                        .append('"').append("Specialty").append('"').append(":").append('"')
                        .append(reception.getDoctor().getSpecialty())
                        .append('"').append(",")
                        .append('"').append("id_reception").append('"').append(":").append('"')
                        .append(reception.getId()).append('"').append("},");
                }else{
                    json.append("{" + '"' + "comments" + '"' + ":" + '"').append(reception.getComments()).append('"').append(",")
                            .append('"').append("date")
                            .append('"').append(":").append('"').append(reception.getDate_reception()).append('"').append(",")
                            .append('"').append("diagnosis")
                            .append('"').append(":").append('"').append(reception.getDiagnosis()).append('"')
                            .append(",").append('"').append("F_L_patient").append('"')
                            .append(":").append('"').append(reception.getPatient().getUser().getLastName()).append(" ")
                            .append(reception.getPatient().getUser()
                                    .getFirstName()).append('"').append(",").append('"').append("time_").append('"').append(":")
                            .append('"').append(reception.getTime())
                            .append('"').append(",")
                            .append('"').append("Doctor").append('"').append(":").append('"')
                            .append("Doctor delete")
                            .append('"').append(",")
                            .append('"').append("Name_Hospital").append('"').append(":").append('"')
                            .append("Doctor delete")
                            .append('"').append(",")
                            .append('"').append("Specialty").append('"').append(":").append('"')
                            .append("Doctor delete")
                            .append('"').append(",")
                            .append('"').append("id_reception").append('"').append(":").append('"')
                            .append(reception.getId()).append('"').append("},");
                }
            }
            json.append("]");
            json = new StringBuilder(json.toString().replace("},]", "}]"));
            response.put("reception",json);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            response.put("reception","data not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
}
