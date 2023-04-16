package org.pl.user.module.adapter.data.repositories;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.user.module.adapter.data.model.UserEnt;
import org.pl.user.module.adapter.data.model.UserEnt_;
import org.pl.user.module.adapter.data.model.exceptions.RepositoryEntException;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class UserEntRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public UserEnt saveUser(UserEnt userEnt) throws RepositoryEntException {
        userEnt.setId(UUID.randomUUID());
        try {
            if (!entityManager.contains(userEnt)) {
                userTransaction.begin();
                entityManager.persist(userEnt);
                userTransaction.commit();
                return userEnt;
            }
        } catch (Exception e) {
            throw new RepositoryEntException(e.getMessage());
        }
        throw new RepositoryEntException(RepositoryEntException.REPOSITORY_ADD_EXCEPTION);
    }

    public UserEnt getUserById(UUID uuid) throws RepositoryEntException {
        try {
            UserEnt userEnt = entityManager.find(UserEnt.class, uuid);
            if (userEnt != null) {
                return userEnt;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public UserEnt updateUser(UUID id, UserEnt userEnt) throws RepositoryEntException {
        try {
            UserEnt userToChange = entityManager.find(UserEnt.class, id);
            userToChange.setFirstName(userEnt.getFirstName());
            userToChange.setLastName(userEnt.getLastName());
            userToChange.setAddressEnt(userEnt.getAddressEnt());
            userToChange.setPhoneNumber(userEnt.getPhoneNumber());
            userToChange.setArchive(userEnt.getArchive());
            userToChange.setUsername(userEnt.getUsername());
            userTransaction.begin();
            entityManager.merge(userToChange);
            userTransaction.commit();
            return userToChange;
        } catch (Exception ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public UserEnt changePassword(UUID id, String newPassword) throws RepositoryEntException {
        try {
            UserEnt userToChange = entityManager.find(UserEnt.class, id);
            userTransaction.begin();
            userToChange.setPassword(newPassword);
            entityManager.merge(userToChange);
            userTransaction.commit();
            return userToChange;
        } catch (Exception ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public UserEnt deleteUser(UUID id) throws RepositoryEntException {
        try {
            UserEnt userEnt = getUserById(id);
            if (!userEnt.getArchive()) {
                userTransaction.begin();
                userEnt.setArchive(true);
                entityManager.merge(userEnt);
                userTransaction.commit();
                return userEnt;
            } else {
                throw new RepositoryEntException(RepositoryEntException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (NullPointerException | NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public List<UserEnt> getUsers(boolean condition) {
        List<UserEnt> userEntList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEnt> criteriaQuery = criteriaBuilder.createQuery(UserEnt.class);
        Root<UserEnt> root = criteriaQuery.from(UserEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(UserEnt_.ARCHIVE), condition));
        TypedQuery<UserEnt> query = entityManager.createQuery(criteriaQuery);
        userEntList = query.getResultList();
        return userEntList;
    }

    public List<UserEnt> getAllUsers() {
        List<UserEnt> userEntList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEnt> criteriaQuery = criteriaBuilder.createQuery(UserEnt.class);
        Root<UserEnt> root = criteriaQuery.from(UserEnt.class);
        criteriaQuery.select(root);
        TypedQuery<UserEnt> query = entityManager.createQuery(criteriaQuery);
        userEntList = query.getResultList();
        return userEntList;
    }

    public UserEnt getUserByUsername(String username) throws RepositoryEntException {
        UserEnt userEnt;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEnt> criteriaQuery = criteriaBuilder.createQuery(UserEnt.class);
        Root<UserEnt> root = criteriaQuery.from(UserEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(UserEnt_.USERNAME), username));
        TypedQuery<UserEnt> query = entityManager.createQuery(criteriaQuery);
        try {
            userEnt = query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_BY_USERNAME_EXCEPTION);
        }
        return userEnt;
    }

    public List<UserEnt> getAllUsersFilter(String substr) {
        List<UserEnt> userEntList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEnt> criteriaQuery = criteriaBuilder.createQuery(UserEnt.class);
        Root<UserEnt> root = criteriaQuery.from(UserEnt.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(UserEnt_.ID), "%" + substr + "%"));
        TypedQuery<UserEnt> query = entityManager.createQuery(criteriaQuery);
        userEntList = query.getResultList();
        return userEntList;
    }

    public List<UserEnt> getUsersByUsername(String username) {
        List<UserEnt> userEntList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEnt> criteriaQuery = criteriaBuilder.createQuery(UserEnt.class);
        Root<UserEnt> root = criteriaQuery.from(UserEnt.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(UserEnt_.USERNAME), "%" + username + "%"));
        TypedQuery<UserEnt> query = entityManager.createQuery(criteriaQuery);
        userEntList = query.getResultList();
        return userEntList;
    }

    public UserEnt restoreUser(UUID uuid) throws RepositoryEntException {
        try {
            UserEnt userEnt = entityManager.find(UserEnt.class, uuid);
            if (userEnt.getArchive()) {
                userTransaction.begin();
                userEnt.setArchive(false);
                entityManager.merge(userEnt);
                userTransaction.commit();
                return userEnt;
            } else {
                throw new RepositoryEntException(RepositoryEntException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (NullPointerException | NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
    }
}