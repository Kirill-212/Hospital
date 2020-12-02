package by.bolvako.Hospital.model;

import javax.persistence.*;
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne( cascade = CascadeType.ALL, orphanRemoval =
            true)
    private User user;
    @Column(name = "specialty")
    private String specialty;
    @Column(name = "Name_Hospital")
    private String Name_Hospital;
    @Column(name = "Passport")
    private String Passport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getName_Hospital() {
        return Name_Hospital;
    }

    public void setName_Hospital(String name_Hospital) {
        Name_Hospital = name_Hospital;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }
}
