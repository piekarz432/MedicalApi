package pl.com.britenet.javastepone.service.patient;

import pl.com.britenet.javastepone.service.dtos.patient.GetPatientDto;
import pl.com.britenet.javastepone.service.dtos.patient.PostPatientDto;

public interface IPatientService {
    GetPatientDto create(PostPatientDto postPatientDto);

    GetPatientDto delete(Long patientId);

    GetPatientDto getByPersonalIdentityNumber(String personalIdentifyNumber);

    GetPatientDto update(Long id, String name, String surname);
}
