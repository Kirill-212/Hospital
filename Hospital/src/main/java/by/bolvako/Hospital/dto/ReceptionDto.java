package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Reception;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//https://habr.com/ru/post/68318/
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceptionDto {
    @NotNull(message = "id doctor cannot be null")
    private Long id_doctor;
    @NotNull(message = "id patient cannot be null")
    private Long id_patient;
    @NotNull(message="time cannot be null")
    @Pattern(regexp = "^{1}[^34567890]{1}[^4983]:{1}[^7896]{1}\\d$",
            message = "time is not valid")
    private String time;
    @NotNull(message="date cannot be null")
    private String Date ;
    @NotNull(message="symptoms cannot be null")
    private String symptoms;

    public ReceptionDto() {
    }

    public Long getId_patient() {
        return id_patient;
    }

    public void setId_patient(Long id_patient) {
        this.id_patient = id_patient;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Long getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(Long id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
