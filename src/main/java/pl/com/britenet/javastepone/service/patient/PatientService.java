package pl.com.britenet.javastepone.service.patient;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.britenet.javastepone.configuration.security.annotation.OtherAuthorization;
import pl.com.britenet.javastepone.repository.PatientRepository;
import pl.com.britenet.javastepone.repository.model.patient.PatientEntity;
import pl.com.britenet.javastepone.service.dtos.patient.GetPatientDto;
import pl.com.britenet.javastepone.service.dtos.patient.PostPatientDto;
import pl.com.britenet.javastepone.service.validator.PatientValidator;

@Service
@Transactional
@OtherAuthorization
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    private final ModelMapper modelMapper;

    private final PatientValidator patientValidator;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper, PatientValidator patientValidator) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
        this.patientValidator = patientValidator;
    }

    @Override
    public GetPatientDto create(PostPatientDto postPatientDto) {

        patientValidator.createValidate(postPatientDto);

        var patientEntity = modelMapper.map(postPatientDto, PatientEntity.class);

        var savedPatient = patientRepository.save(patientEntity);

        return modelMapper.map(savedPatient, GetPatientDto.class);
    }

    @Override
    public GetPatientDto delete(Long patientId) {

        var patient = patientValidator.deleteValidate(patientId);

        patientRepository.deleteById(patientId);

        return modelMapper.map(patient.orElseThrow(), GetPatientDto.class);
    }

    @Override
    public GetPatientDto getByPersonalIdentityNumber(String personalIdentifyNumber) {

        var patient = patientValidator.getByPersonalIdentityNumberValidate(personalIdentifyNumber);

        return modelMapper.map(patient.orElseThrow(), GetPatientDto.class);

    }

    @Override
    public GetPatientDto update(Long id, String name, String surname) {

        var patient = patientValidator.updateValidation(id, name, surname);

        return modelMapper.map(patient.orElseThrow(), GetPatientDto.class);
    }

}
