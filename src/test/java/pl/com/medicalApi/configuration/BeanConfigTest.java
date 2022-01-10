package pl.com.medicalApi.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.com.medicalApi.repository.model.patient.PatientEntity;
import pl.com.medicalApi.service.dtos.patient.GetPatientDto;
import pl.com.medicalApi.service.dtos.patient.PostPatientDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.com.medicalApi.utils.PatientTestUtils.createValidPatientEntity;
import static pl.com.medicalApi.utils.PatientTestUtils.createValidPostPatientDto;

class BeanConfigTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @DisplayName("Convert patient entity to get patient dto")
    @Test
    void shouldDoConvertPatientEntityToGetPatientDto() {
        //given
        var patientEntity = createValidPatientEntity();

        //when
        var getPatientDto = modelMapper.map(patientEntity, GetPatientDto.class);

        //then
        comparePatientEntityWithGetPatientDto(patientEntity, getPatientDto);
    }

    @DisplayName("Convert post patient dto to patient entity")
    @Test
    void shouldDoConvertPostPatientDtoToPatientEntity() {
        //given
        var postPatientDto = createValidPostPatientDto();

        //when
        var patientEntity = modelMapper.map(postPatientDto, PatientEntity.class);

        //then
        comparePostPatientDtoWithPatientEntity(postPatientDto, patientEntity);
    }

    private void comparePatientEntityWithGetPatientDto(PatientEntity patientEntity, GetPatientDto getPatientDto) {
        assertEquals(patientEntity.getId(), getPatientDto.getId());
        assertEquals(patientEntity.getName(), getPatientDto.getName());
        assertEquals(patientEntity.getSurname(), getPatientDto.getSurname());
        assertEquals(patientEntity.getDate(), getPatientDto.getDate());
        assertEquals(patientEntity.getGender(), getPatientDto.getGender());
        assertEquals(patientEntity.getPersonalIdentityNumber(), getPatientDto.getPersonalIdentityNumber());
    }

    private void comparePostPatientDtoWithPatientEntity(PostPatientDto postPatientDto, PatientEntity patientEntity) {
        assertEquals(postPatientDto.getName(), patientEntity.getName());
        assertEquals(postPatientDto.getSurname(), patientEntity.getSurname());
        assertEquals(postPatientDto.getDate(), patientEntity.getDate());
        assertEquals(postPatientDto.getGender(), patientEntity.getGender());
        assertEquals(postPatientDto.getPersonalIdentityNumber(), patientEntity.getPersonalIdentityNumber());
    }
}