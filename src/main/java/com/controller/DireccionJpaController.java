package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.dto.Direccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Distrito;
import com.dto.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DireccionJpaController implements Serializable {

  public DireccionJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Direccion direccion) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Distrito distritoId = direccion.getDistritoId();
      if (distritoId != null) {
        distritoId = em.getReference(distritoId.getClass(), distritoId.getId());
        direccion.setDistritoId(distritoId);
      }
      Persona personaId = direccion.getPersonaId();
      if (personaId != null) {
        personaId = em.getReference(personaId.getClass(), personaId.getId());
        direccion.setPersonaId(personaId);
      }
      em.persist(direccion);
      if (distritoId != null) {
        distritoId.getDireccionList().add(direccion);
        distritoId = em.merge(distritoId);
      }
      if (personaId != null) {
        personaId.getDireccionList().add(direccion);
        personaId = em.merge(personaId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Direccion direccion) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Direccion persistentDireccion = em.find(Direccion.class, direccion.getId());
      Distrito distritoIdOld = persistentDireccion.getDistritoId();
      Distrito distritoIdNew = direccion.getDistritoId();
      Persona personaIdOld = persistentDireccion.getPersonaId();
      Persona personaIdNew = direccion.getPersonaId();
      if (distritoIdNew != null) {
        distritoIdNew = em.getReference(distritoIdNew.getClass(), distritoIdNew.getId());
        direccion.setDistritoId(distritoIdNew);
      }
      if (personaIdNew != null) {
        personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
        direccion.setPersonaId(personaIdNew);
      }
      direccion = em.merge(direccion);
      if (distritoIdOld != null && !distritoIdOld.equals(distritoIdNew)) {
        distritoIdOld.getDireccionList().remove(direccion);
        distritoIdOld = em.merge(distritoIdOld);
      }
      if (distritoIdNew != null && !distritoIdNew.equals(distritoIdOld)) {
        distritoIdNew.getDireccionList().add(direccion);
        distritoIdNew = em.merge(distritoIdNew);
      }
      if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
        personaIdOld.getDireccionList().remove(direccion);
        personaIdOld = em.merge(personaIdOld);
      }
      if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
        personaIdNew.getDireccionList().add(direccion);
        personaIdNew = em.merge(personaIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Long id = direccion.getId();
        if (findDireccion(id) == null) {
          throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Long id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Direccion direccion;
      try {
        direccion = em.getReference(Direccion.class, id);
        direccion.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
      }
      Distrito distritoId = direccion.getDistritoId();
      if (distritoId != null) {
        distritoId.getDireccionList().remove(direccion);
        distritoId = em.merge(distritoId);
      }
      Persona personaId = direccion.getPersonaId();
      if (personaId != null) {
        personaId.getDireccionList().remove(direccion);
        personaId = em.merge(personaId);
      }
      em.remove(direccion);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Direccion> findDireccionEntities() {
    return findDireccionEntities(true, -1, -1);
  }

  public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
    return findDireccionEntities(false, maxResults, firstResult);
  }

  private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Direccion.class));
      Query q = em.createQuery(cq);
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public Direccion findDireccion(Long id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Direccion.class, id);
    } finally {
      em.close();
    }
  }

  public int getDireccionCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Direccion> rt = cq.from(Direccion.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
