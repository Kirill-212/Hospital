package by.bolvako.Hospital.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "receptions")
public class Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)

    private Doctor doctor;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Patient patient;
    @Column(name = "time")
    private String time;
    @Column(name = "date_reception")
    private String date_reception;
    @Column(name = "symptoms")
    private String symptoms;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "comments")
    private String comments;

    public Reception() {
    }

    @Override
    public String toString() {
        return "Reception{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", time='" + time + '\'' +
                ", date_reception='" + date_reception + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate_reception() {
        return date_reception;
    }

    public void setDate_reception(String date_reception) {
        this.date_reception = date_reception;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
