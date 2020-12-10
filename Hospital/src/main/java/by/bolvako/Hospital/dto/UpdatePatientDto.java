package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatePatientDto {
    @NotNull(message = "id cannot be null")
    private  Long id;
    @NotNull(message = "Home address cannot be null")
    private String Homeadress;
    @Size(min = 7, max = 8, message = "The Passport must be equal or greater than 7 characters and less than 8 characters")
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



    public UpdatePatientDto() {
    }

    public UpdatePatientDto(Long id, String homeadress, String passport) {
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
