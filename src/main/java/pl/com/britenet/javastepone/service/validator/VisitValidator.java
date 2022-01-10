package pl.com.britenet.javastepone.service.validator;

import org.springframework.stereotype.Component;
import pl.com.britenet.javastepone.exception.ResourceNotFoundException;
import pl.com.britenet.javastepone.exception.ValidationError;
import pl.com.britenet.javastepone.exception.ValidationException;
import pl.com.britenet.javastepone.repository.MedicalProcedureRepository;
import pl.com.britenet.javastepone.repository.PatientRepository;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.repository.model.visit.Payment;
import pl.com.britenet.javastepone.service.dtos.visit.PostVisitDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class VisitValidator {

    static final String FIELD_DATE = "Date";
    static final String FIELD_TIME = "Time";
    static final String FIELD_PAYMENT = "Payment";
    static final String FIELD_STATUS = "Status";
    static final String FIELD_MEDICAL_PROCEDURE = "Medical procedure";

    private final PatientRepository patientRepository;

    private final MedicalProcedureRepository medicalProcedureRepository;

    public VisitValidator(PatientRepository patientRepository, MedicalProcedureRepository medicalProcedureRepository) {
        this.patientRepository = patientRepository;
        this.medicalProcedureRepository = medicalProcedureRepository;
    }

    public void validateOnCreate(Long patientId, PostVisitDto postVisitDto) {
        var fieldErrorList = isValid(postVisitDto);

        if (fieldErrorList.isEmpty()) {
            patientExists(patientId);

            var numberOfMedicalProcedure = medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure());

            if (numberOfMedicalProcedure == 0) {
                throw new ResourceNotFoundException("Medical procedure with id: " + postVisitDto.getMedicalProcedure() + "  does not exists");
            }
        } else {
            throw new ValidationException("Validation Error", fieldErrorList);
        }
    }

    public void validateOnGetAll(Long patientId) {
        patientExists(patientId);
    }

    public void validateOnGetDetails(Long patientId) {
        patientExists(patientId);
    }

    public void validateOnUpdate(Long patientId, LocalDate date, LocalTime time) {

        var fieldErrorList = new ArrayList<ValidationError>();

        fieldErrorList.addAll(validDate(date));
        fieldErrorList.addAll(validTime(time));

        if (fieldErrorList.isEmpty()) {
            patientExists(patientId);
        } else {
            throw new ValidationException("Validation Error", fieldErrorList);
        }
    }

    public void validateOnCancel(Long patientId) {
        patientExists(patientId);
    }

    private List<ValidationError> isValid(PostVisitDto postVisitDto) {
        var fieldErrorList = new ArrayList<ValidationError>();

        fieldErrorList.addAll(validDate(postVisitDto.getDate()));
        fieldErrorList.addAll(validTime(postVisitDto.getTime()));
        fieldErrorList.addAll(validPayment(postVisitDto.getPayment()));
        fieldErrorList.addAll(validStatus(postVisitDto.getStatus()));
        fieldErrorList.addAll(validMedicineProcedure(postVisitDto.getMedicalProcedure()));

        return fieldErrorList;
    }

    private List<ValidationError> validDate(LocalDate localDate) {
        if (localDate == null) {
            return List.of(new ValidationError(FIELD_DATE, "The date is blank", "The date cannot be empty"));
        }
        if (localDate.isBefore(LocalDate.now())) {
            return List.of(new ValidationError(FIELD_DATE, "The date is incorrect", "The date cannot be before the current date"));
        }

        return emptyList();
    }

    private List<ValidationError> validPayment(Payment payment) {
        if (payment == null) {
            return List.of((new ValidationError(FIELD_PAYMENT, "The payment is blank", "The payment cannot be empty")));
        }

        return emptyList();
    }

    private List<ValidationError> validStatus(Status status) {
        if (status == null) {
            return List.of((new ValidationError(FIELD_PAYMENT, "Staus is blank", "Status cannot be empty")));
        }
        if (status == Status.INACTIVE) {
            return List.of(new ValidationError(FIELD_STATUS, "Staus is inactive", "A new visit can not be status inactive"));
        }
        if (status == Status.DELETED) {
            return List.of(new ValidationError(FIELD_STATUS, "Staus is deleted", "A new visit can not be status deleted"));
        }
        return emptyList();
    }

    private List<ValidationError> validTime(LocalTime localTime) {
        if (localTime == null) {
            return List.of(new ValidationError(FIELD_TIME, "The time is blank", "The date cannot be empty"));
        }
        if (localTime.isBefore(LocalTime.now())) {
            return List.of(new ValidationError(FIELD_TIME, "The time is incorrect", "The time cannot be before the current date"));
        }

        return emptyList();
    }

    private List<ValidationError> validMedicineProcedure(Long id) {
        var count = medicalProcedureRepository.countById(id);

        if (count == 0) {
            return List.of(new ValidationError(FIELD_MEDICAL_PROCEDURE, "Medical procedure is incorrect", "Medical procedure don't exists"));
        }

        return emptyList();
    }

    private void patientExists(Long patientId) {
        var count = patientRepository.countPatientEntityById(patientId);

        if (count == 0) {
            throw new ResourceNotFoundException("Patient with id " + patientId + " does not exists");
        }
    }
}
