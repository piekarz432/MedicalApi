package pl.com.britenet.javastepone.utils;

import pl.com.britenet.javastepone.repository.model.patient.Gender;
import pl.com.britenet.javastepone.repository.model.patient.PatientEntity;
import pl.com.britenet.javastepone.service.dtos.patient.PostPatientDto;

import java.time.LocalDate;

public class PatientTestUtils {

    public static final String patientPath = "/patient";

    public static PostPatientDto createValidPostPatientDto() {
        PostPatientDto postPatientDto = new PostPatientDto();
        postPatientDto.setName("Adam");
        postPatientDto.setSurname("Kowalski");
        postPatientDto.setGender(Gender.MALE);
        postPatientDto.setDate(LocalDate.of(1987, 6, 12));
        postPatientDto.setPersonalIdentityNumber("56511312233");

        return postPatientDto;
    }

    public static PatientEntity createNotExistsPatient() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(2L);
        patientEntity.setName("Adam");
        patientEntity.setSurname("Kowalski");
        patientEntity.setGender(Gender.MALE);
        patientEntity.setDate(LocalDate.of(1987, 6, 12));
        patientEntity.setPersonalIdentityNumber("56511312233");

        return patientEntity;
    }

    public static Long createExistsPatient() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(2L);

        return patientEntity.getId();
    }

    public static PatientEntity createValidPatientEntity() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(1L);
        patientEntity.setName("Adam");
        patientEntity.setSurname("Kowalski");
        patientEntity.setGender(Gender.MALE);
        patientEntity.setDate(LocalDate.of(1987, 6, 12));
        patientEntity.setPersonalIdentityNumber("98909022345");

        return patientEntity;
    }
}
