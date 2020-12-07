package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto {
    @NotNull(message = "id cannot be null")
    private  Long id;
    @NotNull(message = "Home adress cannot be null")
    private String Homeadress;
    @NotNull(message = "Passport cannot be null")
    private String Passport;

    public Patient toPatient(){
        System.out.println(id +"  "+Homeadress+"   "+Passport+"   ");

        Patient patient=new Patient();
        patient.setId(id);
        patient.setHomeadress(Homeadress);
        patient.setPassport(Passport);
        return patient;
    }

    public static PatientDto frompatient(Patient patient) {
        PatientDto patientDto=new PatientDto();
        patientDto.setHomeadress(patient.getHomeadress());
        patientDto.setPassport(patient.getPassport());
        patientDto.setId(patient.getId());
        return patientDto;
    }



    public PatientDto() {
    }

    public PatientDto(Long id, String homeadress, String passport) {
        this.id = id;
        Homeadress = homeadress;
        Passport = passport;
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "id=" + id +
                ", Homeadress='" + Homeadress + '\'' +
                ", Passport='" + Passport + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeadress() {
        return Homeadress;
    }

    public void setHomeadress(String homeadress) {
        Homeadress = homeadress;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }
}
