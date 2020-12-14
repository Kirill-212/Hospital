package by.bolvako.Hospital.dto;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.print.Doc;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDto {
    @NotNull(message = "id cannot be null")
    private Long id;
    @NotNull(message = "Name_Hospital cannot be null")
    private String Name_Hospital;
    @NotNull(message = "specialty cannot be null")
    private String specialty;

    @NotNull(message = "phone number cannot be null")
    private  String Passport;

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", specialty='" + specialty + '\'' +
                ", Name_Hospital='" + Name_Hospital + '\'' +
                ", Passport='" + Passport + '\'' +
                '}';
    }

    public Doctor toDoctor(){
        System.out.println(id +"  "+specialty+"   "+Name_Hospital+"   "+Passport+"  ");

       Doctor doc=new Doctor();
       doc.setId(id);
       doc.setSpecialty(specialty);
       doc.setName_Hospital(Name_Hospital);
       doc.setPassport(Passport);
        return doc;
    }

    public static DoctorDto fromdoctor(Doctor doctor) {
        DoctorDto doctorDto=new DoctorDto();
       doctorDto.setName_Hospital(doctor.getName_Hospital());
        doctorDto.setPassport(doctor.getPassport());
        doctorDto.setSpecialty(doctor.getSpecialty());
        doctorDto.setId(doctor.getId());

        return doctorDto;
    }

    public DoctorDto(Long id, String specialty, String Name_Hospital, String Passport) {
        this.id = id;
        this.specialty = specialty;
        this.Name_Hospital = Name_Hospital;
        this.Passport = Passport;
    }
    public DoctorDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_Hospital() {
        return Name_Hospital;
    }

    public void setName_Hospital(String name_Hospital) {
        Name_Hospital = name_Hospital;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }
}
