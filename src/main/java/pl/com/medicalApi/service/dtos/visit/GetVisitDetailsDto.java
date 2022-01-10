package pl.com.medicalApi.service.dtos.visit;

import pl.com.medicalApi.repository.model.medical_procedure.MedicalProcedureEntity;

public class GetVisitDetailsDto extends GetVisitDto {
    private MedicalProcedureEntity medicalProcedureEntity;

    public MedicalProcedureEntity getMedicalProcedureEntity() {
        return medicalProcedureEntity;
    }

    public void setMedicalProcedureEntity(MedicalProcedureEntity medicalProcedureEntity) {
        this.medicalProcedureEntity = medicalProcedureEntity;
    }
}
