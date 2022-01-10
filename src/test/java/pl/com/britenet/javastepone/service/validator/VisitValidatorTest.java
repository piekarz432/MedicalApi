package pl.com.britenet.javastepone.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.com.britenet.javastepone.exception.ValidationException;
import pl.com.britenet.javastepone.repository.MedicalProcedureRepository;
import pl.com.britenet.javastepone.repository.PatientRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static pl.com.britenet.javastepone.utils.PatientTestUtils.createExistsPatient;
import static pl.com.britenet.javastepone.utils.VisitTestUtils.createValidPostVisitDto;

class VisitValidatorTest {

    @InjectMocks
    private VisitValidator visitValidator;

    @Mock
    private MedicalProcedureRepository medicalProcedureRepository;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @DisplayName("Should create valid visit")
    @Test
    void validateOnCreate_WhenVisitIsValid_ShouldExceptionWillBeNotThrown() {

        //given
        var postVisitDto = createValidPostVisitDto();
        var patientId = createExistsPatient();

        //when
        when(medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure())).thenReturn(1L);
        when(patientRepository.countPatientEntityById(patientId)).thenReturn(1);
        visitValidator.validateOnCreate(patientId, postVisitDto);

        //then
        assertDoesNotThrow(() -> {
        });
    }

    @DisplayName("Create visit with invalid date")
    @Test
    void validateOnCreate_WhenVisitDateIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        var postVisitDto = createValidPostVisitDto();
        postVisitDto.setDate(LocalDate.of(2019,
                10,
                25));

        //when
        when(medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure())).thenReturn(1L);
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnCreate(patientId, postVisitDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create visit with invalid payment")
    @Test
    void validateOnCreate_WhenVisitPaymentIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        var postVisitDto = createValidPostVisitDto();
        postVisitDto.setPayment(null);

        //when
        when(medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure())).thenReturn(1L);
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnCreate(patientId, postVisitDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create visit with invalid status")
    @Test
    void validateOnCreate_WhenVisitStatusIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        var postVisitDto = createValidPostVisitDto();
        postVisitDto.setStatus(null);

        //when
        when(medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure())).thenReturn(1L);
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnCreate(patientId, postVisitDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create visit with invalid medical procedure id")
    @Test
    void validateOnCreate_WhenVisitMedicalProcedureIdIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        var postVisitDto = createValidPostVisitDto();
        postVisitDto.setMedicalProcedure(null);

        //when
        when(medicalProcedureRepository.countById(postVisitDto.getMedicalProcedure())).thenReturn(0L);
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnCreate(patientId, postVisitDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Update visit with invalid date")
    @Test
    void validateOnUpdate_WhenDateIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        LocalDate date = LocalDate.of(2020, 11, 11);
        LocalTime time = LocalTime.of(16, 45);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnUpdate(patientId, date, time));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Update visit with invalid time")
    @Test
    void validateOnUpdate_WhenTimeIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(7, 45);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnUpdate(patientId, date, time));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Update visit with valid date and time")
    @Test
    void validateOnUpdate_WhenDateAndTimeIsValid_ShouldExceptionWillBeNotThrown() {

        //given
        var patientId = createExistsPatient();
        LocalDate date = LocalDate.of(2022, 11, 11);
        LocalTime time = LocalTime.of(19, 45);

        //when
        when(patientRepository.countPatientEntityById(patientId)).thenReturn(1);
        visitValidator.validateOnUpdate(patientId, date, time);

        //then
        assertDoesNotThrow(() -> {
        });
    }

    @DisplayName("Update visit with invalid date and time")
    @Test
    void validateOnUpdate_WhenDateAndTimeIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var patientId = createExistsPatient();
        LocalDate date = LocalDate.of(2020, 11, 11);
        LocalTime time = LocalTime.of(5, 45);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> visitValidator.validateOnUpdate(patientId, date, time));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }
}




