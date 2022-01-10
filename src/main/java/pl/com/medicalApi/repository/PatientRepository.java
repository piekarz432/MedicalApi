package pl.com.medicalApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.medicalApi.repository.model.patient.PatientEntity;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findPatientByPersonalIdentityNumber(String personalIdentifyNumber);

    int countPatientEntityByPersonalIdentityNumber(String personalIdentifyNumber);

    int countPatientEntityById(Long id);
}
