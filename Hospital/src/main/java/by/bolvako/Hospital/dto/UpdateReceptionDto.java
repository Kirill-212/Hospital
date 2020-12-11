package by.bolvako.Hospital.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateReceptionDto {
    @NotNull(message = "id reception cannot be null")
    private Long id;
    @NotNull(message = "diagnosis reception cannot be null")
    private String diagnosis;
    @NotNull(message = "comments reception cannot be null")
    private String comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
