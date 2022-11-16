package org.pl.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaUpdate;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Repair;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RepairRepository {

    private EntityManager entityManager;

    public Repair saveRepair(Repair repair) {
        if (!entityManager.contains(repair)) {
            entityManager.persist(repair);
        } else {
            entityManager.merge(repair);
        }
        return repair;
    }

    public Repair getRepairById(UUID uuid) throws RepositoryException {
        Repair repair = entityManager.find(Repair.class, uuid);
        if (repair != null) {
            return repair;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public Repair deleteRepair(UUID id) throws RepositoryException {
        entityManager.getTransaction().begin();
        Repair repair = entityManager.find(Repair.class, id);
        if (repair != null) {
            if (!repair.isArchive()) {
                entityManager.merge(repair);
                repair.setArchive(true);
                entityManager.getTransaction().commit();
                return repair;
            }
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public List<Repair> getClientRepairs(UUID clientId) {
        List<Repair> repairs = new ArrayList<>();
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder criteriaBuilder = (CriteriaBuilder) session.getCriteriaBuilder();
            CriteriaQuery<Repair> criteriaQuery = criteriaBuilder.createQuery(Repair.class);
            Root<Repair> root = criteriaQuery.from(Repair.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("client_id"), clientId));
            Query<Repair> query = session.createQuery((CriteriaUpdate) criteriaQuery);
            repairs = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repairs;
    }
}
