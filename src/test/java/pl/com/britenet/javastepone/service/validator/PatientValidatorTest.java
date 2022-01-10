package pl.com.britenet.javastepone.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.com.britenet.javastepone.exception.ResourceFoundException;
import pl.com.britenet.javastepone.exception.ResourceNotFoundException;
import pl.com.britenet.javastepone.exception.ValidationException;
import pl.com.britenet.javastepone.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static pl.com.britenet.javastepone.utils.PatientTestUtils.createNotExistsPatient;
import static pl.com.britenet.javastepone.utils.PatientTestUtils.createValidPostPatientDto;

class PatientValidatorTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientValidator patientValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @DisplayName("Should create valid patient")
    @Test
    void createValidate_WhenPatientIsValid_ShouldExceptionWillBeNotThrown() {

        //given
        var postPatientDto = createValidPostPatientDto();

        //when
        patientValidator.createValidate(postPatientDto);

        //then
        assertDoesNotThrow(() -> {
        });
    }

    @DisplayName("Create patient with invalid name")
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"A", "qweqwrqwrqrwasdasdwqrqqwasfazxcawrqwsaf"})
    void createValidate_WhenPatientNameIsInvalid_ShouldExceptionWillBeThrown(String name) {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setName(name);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> patientValidator.createValidate(postPatientDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create patient with invalid surname")
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"A", "qweqwrqwrqrwasdasdwqrqqwasfazxcawrqwsaf"})
    void createValidate_WhenPatientSurnameIsInvalid_ShouldExceptionWillBeThrown(String surname) {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setName(surname);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> patientValidator.createValidate(postPatientDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create patient with invalid date")
    @Test
    void createValidate_WhenPatientDateIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setDate(LocalDate.of(2022, 10, 25));

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> patientValidator.createValidate(postPatientDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create patient with invalid gender")
    @Test
    void createValidate_WhenPatientGenderIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setGender(null);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> patientValidator.createValidate(postPatientDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create patient with invalid personal identity number")
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"9898903", "989090112345678", "9876154ABC"})
    void createValidate_WhenPatientPersonalIdentityNumberIsInvalid_ShouldExceptionWillBeThrown(String personalIdentityNumber) {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setPersonalIdentityNumber(personalIdentityNumber);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> patientValidator.createValidate(postPatientDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create patient when patient exists")
    @Test
    void createValidate_WhenPatientIsExists_ShouldExceptionWillBeThrown() {

        //given
        var postPatientDto = createValidPostPatientDto();
        postPatientDto.setPersonalIdentityNumber("16511312233");

        //when
        when(patientRepository.countPatientEntityByPersonalIdentityNumber(postPatientDto.getPersonalIdentityNumber())).thenReturn(1);
        ResourceFoundException exception = assertThrows(ResourceFoundException.class, () -> patientValidator.createValidate(postPatientDto));


        //then
        assertEquals("Patient with personal identity number: " + postPatientDto.getPersonalIdentityNumber() + " exists", exception.getMessage());
    }

    @DisplayName("Delete patient if patient not exists")
    @Test
    void deleteValidate_WhenPatientIsNotExists_ShouldExceptionWillBeThrown() {

        //given
        var patient = createNotExistsPatient();

        //when
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientValidator.deleteValidate(patient.getId()));

        //then
        assertEquals("Patient with id: " + patient.getId() + " does not exists", exception.getMessage());
    }

    @DisplayName("Get patient if patient not exists")
    @Test
    void getByPersonalIdentityNumber_WhenPatientIsNotExists_ShouldExceptionWillBeThrown() {

        //given
        var patient = createNotExistsPatient();

        //when
        when(patientRepository.findPatientByPersonalIdentityNumber(patient.getPersonalIdentityNumber())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientValidator.getByPersonalIdentityNumberValidate(patient.getPersonalIdentityNumber()));

        //then
        assertEquals("Patient with personal identify number: " + patient.getPersonalIdentityNumber() + " does not exists", exception.getMessage());
    }
}