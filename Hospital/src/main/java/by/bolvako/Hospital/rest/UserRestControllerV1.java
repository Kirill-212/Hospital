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

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
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
    public ResponseEntity PostDoctor(@RequestBody AdminUserDto requestDto) {
        List<Doctor> doctors=doctorService.getAll();

        String json="{";
        for(int i=0 ;i<doctors.size();i++){
            json+="{"+'"'+"Name_Hospital"+'"'+":"+'"'+doctors.get(i).getName_Hospital()+'"'+","+'"'+"id"+'"'+":"+'"'+doctors.get(i).getId()+'"'
                    +","+'"'+"Specialty"+'"'+":"+'"'+doctors.get(i).getSpecialty()+'"'+"},";
        }
        json+="}";
        json=json.replace("},}","}}");
        System.out.println(json);
        Map<Object, Object> response = new HashMap<>();
        response.put("doctor",json);
        return ResponseEntity.ok(response);
    }

    @PostMapping("updateReception")
    public ResponseEntity UpdateReception(@Valid @RequestBody UpdateReceptionDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();

        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try{
           Reception reception=receptionService.findById(requestDto.getId());
            if(reception!=null){
                reception.setDiagnosis(requestDto.getDiagnosis());
                reception.setComments(requestDto.getComments());
                receptionCrudRepository.save(reception);
                response.put("userError", "reception update successfully");
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

    @RequestMapping(value = "delReception/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity DeleteUser(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();

        try{
            System.out.println(receptionService.findById(id));
           Reception rec= receptionService.findById(id);
            System.out.println(rec.toString());
            if(rec==null){

                response.put("userError", "reception not found or delete ");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            System.out.println("ff");
            response.put("userError", "reception del successfully");
           receptionService.delete(id);

        }catch (Exception e){
            response.put("userError", "reception not found or delete");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @RequestMapping(value = "doctor/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetAllReceptionForDoctor(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();
        String json="{";
        try {
            List<Reception> rec = receptionService.getByIdDoctor(doctorService.findById(id));

            for (Reception reception : rec) {
                System.out.println(reception.toString());
                json += "{" + '"' + "comments" + '"' + ":" + '"' + reception.getComments() + '"' + "," + '"' + "date" + '"' + ":" + '"' + reception.getDate_reception() + '"' + "," + '"' + "diagnosis" + '"' + ":"
                        + '"' + reception.getDiagnosis() + '"' + "," + '"' + "F_L" + '"' + ":" + '"' + reception.getPatient().getUser().getLastName() + " " + reception.getPatient().getUser().getFirstName() + '"'
                        + "," + '"' + "time_" + '"' + ":" + '"' + reception.getTime() + '"' + "," + '"' + "id" + '"' + ":" + '"' + reception.getId() + '"' + "},";
            }
            json += "}";
            json = json.replace("},}", "}}");
            response.put("reception",json);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            response.put("reception","data not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetAllReceptionForPatient(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();
        StringBuilder json= new StringBuilder("{");
        try {
            List<Reception> rec = receptionService.getByIdPatient(patientService.findById(id));

            for (Reception reception : rec) {
                System.out.println(reception.toString());
                json.append("{" + '"' + "comments" + '"' + ":" + '"').append(reception.getComments()).append('"').append(",").append('"').append("date")
                        .append('"').append(":").append('"').append(reception.getDate_reception()).append('"').append(",").append('"').append("diagnosis")
                        .append('"').append(":").append('"').append(reception.getDiagnosis()).append('"').append(",").append('"').append("F_L").append('"')
                        .append(":").append('"').append(reception.getPatient().getUser().getLastName()).append(" ").append(reception.getPatient().getUser()
                        .getFirstName()).append('"').append(",").append('"').append("time_").append('"').append(":").append('"').append(reception.getTime())
                        .append('"').append(",").append('"').append("id").append('"').append(":").append('"').append(reception.getId()).append('"').append("},");
            }
            json.append("}");
            json = new StringBuilder(json.toString().replace("},}", "}}"));
            response.put("reception",json);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            response.put("reception","data not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
}
