package pl.com.medicalApi.repository;

import org.springframework.stereotype.Repository;
import pl.com.medicalApi.repository.model.medical_procedure.MedicalProcedureEntity;

import javax.persistence.EntityManager;

@Repository
public class MedicalProcedureRepository {

    private static final String COUNT_ID = "SELECT COUNT(M.id) FROM MedicalProcedureEntity M WHERE M.id =:medicalProcedureId";

    private final EntityManager entityManager;

    public MedicalProcedureRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MedicalProcedureEntity findById(Long id) {
        return entityManager.find(MedicalProcedureEntity.class, id);
    }

    public Long countById(Long medicalProcedureId) {
        return entityManager
                .createQuery(COUNT_ID, Long.class)
                .setParameter("medicalProcedureId", medicalProcedureId)
                .getSingleResult();
    }
}
