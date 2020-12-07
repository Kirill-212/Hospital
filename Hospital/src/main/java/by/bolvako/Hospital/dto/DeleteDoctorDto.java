package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteDoctorDto {
    @NotNull(message = "id cannot be null")
    private Long id;

    public Doctor toDoctor(){
        System.out.println(id +"  ");

        Doctor doc=new Doctor();
        doc.setId(id);
        return doc;
    }

    public static DoctorDto fromdoctor(Doctor doctor) {
        DoctorDto doctorDto=new DoctorDto();
        doctorDto.setId(doctor.getId());

        return doctorDto;
    }


    public DeleteDoctorDto() {
    }

    public DeleteDoctorDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
