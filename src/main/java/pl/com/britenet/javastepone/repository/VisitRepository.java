package pl.com.britenet.javastepone.repository;

import org.springframework.stereotype.Repository;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.repository.model.visit.VisitEntity;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VisitRepository {

    private static final String SELECT_ID = "SELECT V FROM VisitEntity V WHERE V.id =:visitId AND V.status =: statusActive";
    private static final String SELECT_ALL_VISIT = "SELECT V FROM VisitEntity V WHERE V.patient.id =: patientId AND V.status =: statusActive";

    private final EntityManager entityManager;

    public VisitRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(VisitEntity visitEntity) {
        entityManager.persist(visitEntity);
    }

    public VisitEntity findById(Long visitId) {
        return entityManager.createQuery(SELECT_ID, VisitEntity.class)
                .setParameter("visitId", visitId)
                .setParameter("statusActive", Status.ACTIVE)
                .getSingleResult();
    }

    public List<VisitEntity> findAllById(Long patientId) {
        return entityManager.createQuery(SELECT_ALL_VISIT, VisitEntity.class)
                .setParameter("patientId", patientId)
                .setParameter("statusActive", Status.ACTIVE)
                .getResultList();
    }
}
