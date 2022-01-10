package pl.com.britenet.javastepone.service.dtos.visit;

import pl.com.britenet.javastepone.repository.model.Status;

public class PostVisitDto extends VisitDto {

    private Status status;
    private Long medicalProcedure;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getMedicalProcedure() {
        return medicalProcedure;
    }

    public void setMedicalProcedure(Long medicalProcedure) {
        this.medicalProcedure = medicalProcedure;
    }
}
