package pl.com.medicalApi.service.dtos.visit;

import pl.com.medicalApi.repository.model.Status;

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
