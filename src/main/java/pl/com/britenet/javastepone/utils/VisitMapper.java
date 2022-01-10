package pl.com.britenet.javastepone.utils;

import pl.com.britenet.javastepone.repository.model.medical_procedure.MedicalProcedureEntity;
import pl.com.britenet.javastepone.repository.model.patient.PatientEntity;
import pl.com.britenet.javastepone.repository.model.visit.VisitEntity;
import pl.com.britenet.javastepone.service.dtos.visit.GetVisitDetailsDto;
import pl.com.britenet.javastepone.service.dtos.visit.GetVisitDto;
import pl.com.britenet.javastepone.service.dtos.visit.PostVisitDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class VisitMapper {

    private VisitMapper() {
    }

    public static VisitEntity mapToVisitEntity(PostVisitDto postVisitDto, Optional<PatientEntity> patientEntity, MedicalProcedureEntity medicalProcedureEntity) {
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setVisitDate(postVisitDto.getDate());
        visitEntity.setVisitTime(postVisitDto.getTime());
        visitEntity.setPayment(postVisitDto.getPayment());
        visitEntity.setStatus(postVisitDto.getStatus());
        visitEntity.setPatient(patientEntity.orElseThrow());
        visitEntity.setMedicalProcedure(medicalProcedureEntity);

        return visitEntity;
    }

    public static GetVisitDto mapToGetVisitDto(VisitEntity visitEntity) {
        GetVisitDto getVisitDto = new GetVisitDto();
        getVisitDto.setId(visitEntity.getId());
        getVisitDto.setDate(visitEntity.getVisitDate());
        getVisitDto.setTime(visitEntity.getVisitTime());
        getVisitDto.setPayment(visitEntity.getPayment());

        return getVisitDto;
    }

    public static GetVisitDetailsDto mapToGetVisitDetailsDto(VisitEntity visitEntity) {
        GetVisitDetailsDto getVisitDetailsDto = new GetVisitDetailsDto();
        getVisitDetailsDto.setId(visitEntity.getId());
        getVisitDetailsDto.setDate(visitEntity.getVisitDate());
        getVisitDetailsDto.setTime(visitEntity.getVisitTime());
        getVisitDetailsDto.setPayment(visitEntity.getPayment());
        getVisitDetailsDto.setMedicalProcedureEntity(visitEntity.getMedicalProcedure());

        return getVisitDetailsDto;
    }

    public static List<GetVisitDto> mapToGetVisitDtoList(List<VisitEntity> visitEntity) {
        List<GetVisitDto> getVisitDto = new ArrayList<>();

        for (VisitEntity v : visitEntity) {
            getVisitDto.add(mapToGetVisitDto(v));
        }

        return getVisitDto;
    }
}
