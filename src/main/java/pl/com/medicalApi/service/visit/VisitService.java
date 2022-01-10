package pl.com.medicalApi.service.visit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.medicalApi.configuration.security.annotation.OtherAuthorization;
import pl.com.medicalApi.repository.MedicalProcedureRepository;
import pl.com.medicalApi.repository.PatientRepository;
import pl.com.medicalApi.repository.VisitRepository;
import pl.com.medicalApi.repository.model.Status;
import pl.com.medicalApi.service.dtos.visit.GetVisitDto;
import pl.com.medicalApi.service.dtos.visit.PostVisitDto;
import pl.com.medicalApi.service.validator.VisitValidator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static pl.com.medicalApi.utils.VisitMapper.*;

@Transactional
@Service
@OtherAuthorization
public class VisitService implements IVisitService {

    private final VisitRepository visitRepository;

    private final PatientRepository patientRepository;

    private final MedicalProcedureRepository medicalProcedureRepository;

    private final VisitValidator visitValidator;

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository, VisitValidator visitValidator, MedicalProcedureRepository medicalProcedureRepository) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.visitValidator = visitValidator;
        this.medicalProcedureRepository = medicalProcedureRepository;
    }

    @Override
    public GetVisitDto create(Long patientId, PostVisitDto postVisitDto) {

        visitValidator.validateOnCreate(patientId, postVisitDto);

        var patient = patientRepository.findById(patientId);

        var medicalProcedure = medicalProcedureRepository.findById(postVisitDto.getMedicalProcedure());

        var visit = mapToVisitEntity(postVisitDto, patient, medicalProcedure);

        patient.orElseThrow().addVisit(visit);

        visitRepository.save(visit);

        return mapToGetVisitDto(visit);
    }

    @Override
    public List<GetVisitDto> getAll(Long patientId) {

        visitValidator.validateOnGetAll(patientId);

        var visit = visitRepository.findAllById(patientId);

        return mapToGetVisitDtoList(visit);
    }

    @Override
    public GetVisitDto getDetails(Long patientId, Long visitId) {

        visitValidator.validateOnGetDetails(patientId);

        var visit = visitRepository.findById(visitId);

        return mapToGetVisitDetailsDto(visit);
    }

    @Override
    public GetVisitDto update(Long patientId, Long visitId, LocalDate date, LocalTime time) {

        visitValidator.validateOnUpdate(patientId, date, time);

        var visit = visitRepository.findById(visitId);
        visit.setVisitDate(date);
        visit.setVisitTime(time);

        return mapToGetVisitDto(visit);
    }

    @Override
    public GetVisitDto cancel(Long patientId, Long visitId) {

        visitValidator.validateOnCancel(patientId);

        var visit = visitRepository.findById(visitId);
        visit.setStatus(Status.INACTIVE);

        return mapToGetVisitDto(visit);
    }
}
