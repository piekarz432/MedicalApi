package pl.com.britenet.javastepone.service.dtos.visit;

import pl.com.britenet.javastepone.repository.model.medical_procedure.MedicalProcedureEntity;

public class GetVisitDetailsDto extends GetVisitDto {
    private MedicalProcedureEntity medicalProcedureEntity;

    public MedicalProcedureEntity getMedicalProcedureEntity() {
        return medicalProcedureEntity;
    }

    public void setMedicalProcedureEntity(MedicalProcedureEntity medicalProcedureEntity) {
        this.medicalProcedureEntity = medicalProcedureEntity;
    }
}
