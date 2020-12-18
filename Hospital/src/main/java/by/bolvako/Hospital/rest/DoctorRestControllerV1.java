package by.bolvako.Hospital.rest;

import by.bolvako.Hospital.Exceptions.UsersValidationException;
import by.bolvako.Hospital.dto.*;
import by.bolvako.Hospital.model.*;
import by.bolvako.Hospital.repository.ReceptionCrudRepository;
import by.bolvako.Hospital.service.DoctorService;
import by.bolvako.Hospital.service.PatientService;
import by.bolvako.Hospital.service.ReceptionService;
import by.bolvako.Hospital.service.UserService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/api/v1/doctor/")

public class DoctorRestControllerV1 {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(UserRestControllerV1.class);
    private final UserService userService;
    private  final DoctorService doctorService;
    private  final PatientService patientService;
    private  final ReceptionService receptionService;
    private  final ReceptionCrudRepository receptionCrudRepository;
    @Autowired
    public DoctorRestControllerV1(UserService userService
            ,ReceptionCrudRepository receptionCrudRepository,DoctorService doctorService,PatientService patientService,ReceptionService receptionService) {
        this.doctorService=doctorService;
        this.userService = userService;
        this.patientService=patientService;
        this.receptionService=receptionService;
        this.receptionCrudRepository=receptionCrudRepository;
    }
    @Operation(summary="Get by doctor id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get all reception for doctor",
                    content = {@Content(mediaType = "application/json")})
    })
    @RequestMapping(value = "doctor/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetAllReceptionForDoctor(@PathVariable(value = "id") Long id){
        Map<Object, Object> response = new HashMap<>();
        log.info("GetAllReceptionForDoctor:/api/v1/users/doctor/{id}");
        StringBuilder json= new StringBuilder("[");
        try {
            List<Reception> rec = receptionService.getByIdDoctor(doctorService.findById(id));

            for (Reception reception : rec) {
                System.out.println(reception.toString());
                if(reception.getPatient()!=null){
                    json.append("{" + '"' + "comments" + '"' + ":" + '"').
                            append(reception.getComments()).append('"').append(",").
                            append('"').append("date").append('"').append(":").append('"').
                            append(reception.getDate_reception()).append('"').append(",").append('"').
                            append("diagnosis").append('"').append(":").append('"').
                            append(reception.getDiagnosis()).append('"').append(",").append('"').
                            append("F_L_PAT").append('"').append(":").append('"').append(reception.getPatient().
                            getUser().getLastName()).append(" ").append(reception.getPatient().getUser().getFirstName()).
                            append('"').append(",").append('"').append("time_").append('"').append(":").append('"').
                            append(reception.getTime()).append('"').append(",").append('"').append("id_reception").
                            append('"').append(":").append('"').append(reception.getId()).append('"').append("},");
                }else{
                    json.append("{" + '"' + "comments" + '"' + ":" + '"').
                            append(reception.getComments()).append('"').append(",").
                            append('"').append("date").append('"').append(":").append('"').
                            append(reception.getDate_reception()).append('"').append(",").append('"').
                            append("diagnosis").append('"').append(":").append('"').
                            append(reception.getDiagnosis()).append('"').append(",").append('"').
                            append("F_L_PAT").append('"').append(":").append('"').append("Patient delete").
                            append('"').append(",").append('"').append("time_").append('"').append(":").append('"').
                            append(reception.getTime()).append('"').append(",").append('"').append("id_reception").
                            append('"').append(":").append('"').append(reception.getId()).append('"').append("},");
                }
            }
            json.append("]");
            json = new StringBuilder(json.toString().replace("},]", "}]"));
            System.out.println("---");
            System.out.println(json);
            System.out.println("---");
            response.put("reception", json.toString());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            response.put("reception","data not found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

    }
    @Operation(summary="Update reception")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update reception",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("updateReception")
    public ResponseEntity UpdateReception(@Valid @RequestBody UpdateReceptionDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();
        log.info("UpdateReception:/api/v1/users/updateReception");
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
    @Operation(summary="Search patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "search patient",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("Search/patient")
    public ResponseEntity SearchReception(@Valid @RequestBody SearchPatientDto requestDto, BindingResult errors) {
        Map<Object, Object> response = new HashMap<>();
        log.info("SearchReception:/api/v1/users/Search/patient");
        if(errors.hasErrors()){
            throw new UsersValidationException(errors);
        }
        try{
            System.out.println(requestDto.getFirstName()+"|"+requestDto.getLastName());
            List<User> users=userService.GetByFandL(requestDto.getFirstName(),requestDto.getLastName());
            if(users!=null){
                StringBuilder json= new StringBuilder("[");
                //   System.out.println(users.get(0).getId()+"|"+users.get(2).getId()+"|"+users.get(1).getId()+"||"+users.size());
                for (User user : users) {
                    if (patientService.findbyUser(user) != -2L) {
                        Patient patient = patientService.findById(patientService.findbyUser(user));
                        json.append("{" + '"' + "Passport" + '"' + ":" + '"').
                                append(patient.getPassport()).append('"').append(",").append('"').
                                append("id").append('"').append(":").append('"').append(patient.getId()).
                                append('"').append(",").append('"').append("Homeadress").append('"')
                                .append(":").append('"').append(patient.getHomeadress()).append('"').
                                append(",").append('"').append("F").append('"').append(":").append('"').
                                append(patient.getUser().getFirstName()).append('"').append(",").append('"')
                                .append("L").append('"').append(":").append('"').append(patient.getUser().
                                getLastName()).append('"').append("},");
                    }
                }
                json.append("]");
                json = new StringBuilder(json.toString().replace("},]", "}]"));
                System.out.println("---");
                System.out.println(json);
                System.out.println("---");
                response.put("patients", json.toString());
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else{
                response.put("userError", "error date patient not found ");
            }
        }catch (Exception e){
            response.put("userError", "check input data or user is not active");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
