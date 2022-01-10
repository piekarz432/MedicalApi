package pl.com.medicalApi.service.validator;

import org.springframework.stereotype.Component;
import pl.com.medicalApi.exception.ResourceFoundException;
import pl.com.medicalApi.exception.ResourceNotFoundException;
import pl.com.medicalApi.exception.ValidationError;
import pl.com.medicalApi.exception.ValidationException;
import pl.com.medicalApi.repository.PatientRepository;
import pl.com.medicalApi.repository.model.patient.Gender;
import pl.com.medicalApi.repository.model.patient.PatientEntity;
import pl.com.medicalApi.service.dtos.patient.PostPatientDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

@Component
public class PatientValidator {

    static final int MIN_NAME_LENGTH = 3;
    static final int MAX_NAME_LENGTH = 25;
    static final int MIN_SURNAME_LENGTH = 3;
    static final int MAX_SURNAME_LENGTH = 25;
    static final int PERSONAL_IDENTITY_NUMBER_LENGTH = 11;
    static final Pattern PERSONAL_IDENTITY_NUMBER_PATTERN = Pattern.compile("[0-9]+");

    static final String FIELD_NAME = "Name";
    static final String FIELD_SURNAME = "Surname";
    static final String FIELD_DATE = "Date";
    static final String FIELD_GENDER = "Gender";
    static final String FIELD_PERSONAL_IDENTITY_NUMBER = "Personal Identity Number";

    private final PatientRepository patientRepository;

    public PatientValidator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void createValidate(PostPatientDto postPatientDto) {
        var fieldErrorList = isValid(postPatientDto);

        if (fieldErrorList.isEmpty()) {
            int count = patientRepository.countPatientEntityByPersonalIdentityNumber(postPatientDto.getPersonalIdentityNumber());

            if (count != 0) {
                throw new ResourceFoundException("Patient with personal identity number: " + postPatientDto.getPersonalIdentityNumber() + " exists");
            }
        } else {
            throw new ValidationException("Validation Error", fieldErrorList);
        }
    }

    public Optional<PatientEntity> deleteValidate(Long patientId) {
        Optional<PatientEntity> patientEntityOptional = patientRepository.findById(patientId);

        if (patientEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Patient with id: " + patientId + " does not exists");
        }

        return patientEntityOptional;
    }

    public Optional<PatientEntity> getByPersonalIdentityNumberValidate(String personalIdentityNumber) {
        Optional<PatientEntity> patientEntityOptional = patientRepository
                .findPatientByPersonalIdentityNumber(personalIdentityNumber);

        if (patientEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Patient with personal identify number: " + personalIdentityNumber + " does not exists");
        }

        return patientEntityOptional;
    }

    public Optional<PatientEntity> updateValidation(Long id, String name, String surname) {

        var fieldErrorList = new ArrayList<ValidationError>();

        Optional<PatientEntity> patientEntityOptional = Optional.empty();

        if (name != null) {
            fieldErrorList.addAll(validName(name));

            if (fieldErrorList.isEmpty()) {
                patientEntityOptional = patientRepository.findById(id);

                if (patientEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Patient with personal identify number: "
                            + patientEntityOptional.orElseThrow().getPersonalIdentityNumber() + " does not exists");
                }
                patientEntityOptional.orElseThrow().setName(name);
            } else {
                throw new ValidationException("Validation Error", fieldErrorList);
            }
        }

        if (surname != null) {
            fieldErrorList.addAll(validSurname(surname));

            if (fieldErrorList.isEmpty()) {
                patientEntityOptional = patientRepository.findById(id);

                if (patientEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Patient with personal identify number: "
                            + patientEntityOptional.orElseThrow().getPersonalIdentityNumber() + " does not exists");
                }
                patientEntityOptional.orElseThrow().setSurname(surname);
            } else {
                throw new ValidationException("Validation Error", fieldErrorList);
            }
        }

        return patientEntityOptional;
    }

    private List<ValidationError> isValid(PostPatientDto postPatientDto) {
        var fieldErrorList = new ArrayList<ValidationError>();

        fieldErrorList.addAll(validName(postPatientDto.getName()));
        fieldErrorList.addAll(validSurname(postPatientDto.getSurname()));
        fieldErrorList.addAll(validDate(postPatientDto.getDate()));
        fieldErrorList.addAll(validGender(postPatientDto.getGender()));
        fieldErrorList.addAll(validPersonalIdentityNumber(postPatientDto.getPersonalIdentityNumber()));

        return fieldErrorList;
    }

    private List<ValidationError> validName(String name) {

        if (name == null || name.equals("")) {
            return List.of(new ValidationError(FIELD_NAME, "The name is blank", "The name cannot be empty"));
        }
        if (name.length() < MIN_NAME_LENGTH) {
            return List.of(new ValidationError(FIELD_NAME, "The name is too short", "The name must be at least 3 characters"));
        }
        if (name.length() > MAX_NAME_LENGTH) {
            return List.of(new ValidationError(FIELD_NAME, "The name is too long", "The name can be up to 25 characters"));
        }

        return emptyList();
    }

    private List<ValidationError> validSurname(String surname) {

        if (surname == null || surname.equals("")) {
            return List.of(new ValidationError(FIELD_SURNAME, "The surname is blank", "The surname cannot be empty"));
        }
        if (surname.length() < MIN_SURNAME_LENGTH) {
            return List.of(new ValidationError(FIELD_SURNAME, "The surname is too short", "The surname must be at least 3 characters"));
        }
        if (surname.length() > MAX_SURNAME_LENGTH) {
            return List.of(new ValidationError(FIELD_SURNAME, "The surname is too long", "The surname can be up to 25 characters"));
        }

        return emptyList();
    }

    private List<ValidationError> validDate(LocalDate localDate) {

        if (localDate == null) {
            return List.of(new ValidationError(FIELD_DATE, "The date is blank", "The date cannot be empty"));
        }
        if (localDate.isAfter(LocalDate.now())) {
            return List.of(new ValidationError(FIELD_DATE, "The date is incorrect", "The date cannot be after the current date"));
        }

        return emptyList();
    }

    private List<ValidationError> validGender(Gender gender) {
        if (gender == null) {
            return List.of((new ValidationError(FIELD_GENDER, "The gender is blank", "The gender cannot be empty")));
        }

        return emptyList();
    }

    private List<ValidationError> validPersonalIdentityNumber(String personalIdentityNumber) {

        if (personalIdentityNumber == null || personalIdentityNumber.equals("")) {
            return List.of(new ValidationError(FIELD_PERSONAL_IDENTITY_NUMBER, "Personal Identity Number is blank", "Personal Identity Number cannot be empty"));
        }
        if (personalIdentityNumber.length() != PERSONAL_IDENTITY_NUMBER_LENGTH) {
            return List.of(new ValidationError(FIELD_PERSONAL_IDENTITY_NUMBER, "Personal Identity Number length is not 11 characters", "Personal Identity Number must be 11 characters "));
        }
        if (!personalIdentityNumber.matches(PERSONAL_IDENTITY_NUMBER_PATTERN.pattern())) {
            return List.of(new ValidationError(FIELD_PERSONAL_IDENTITY_NUMBER, "Personal Identity Number contains invalid characters", "Personal Identity Number it must only consist of numbers "));
        }

        return emptyList();
    }
}
