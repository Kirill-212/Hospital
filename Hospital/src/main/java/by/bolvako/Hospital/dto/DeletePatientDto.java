package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeletePatientDto {
    @NotNull(message = "id cannot be null")
    private Long id;
    public Patient toPatient(){
        System.out.println(id +"  ");

        Patient doc=new Patient();
        doc.setId(id);
        return doc;
    }

    public static PatientDto fromdoctor(Patient patient) {
        PatientDto patientDto=new PatientDto();
        patientDto.setId(patient.getId());

        return patientDto;
    }
    public DeletePatientDto() {
    }

    public DeletePatientDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
