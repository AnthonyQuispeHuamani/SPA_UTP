/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Persona;
import com.dto.Telefono;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luciano
 */
public class TelefonoJpaController implements Serializable {

  public TelefonoJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Telefono telefono) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Persona personaId = telefono.getPersonaId();
      if (personaId != null) {
        personaId = em.getReference(personaId.getClass(), personaId.getId());
        telefono.setPersonaId(personaId);
      }
      em.persist(telefono);
      if (personaId != null) {
        personaId.getTelefonoList().add(telefono);
        personaId = em.merge(personaId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Telefono telefono) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Telefono persistentTelefono = em.find(Telefono.class, telefono.getId());
      Persona personaIdOld = persistentTelefono.getPersonaId();
      Persona personaIdNew = telefono.getPersonaId();
      if (personaIdNew != null) {
        personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
        telefono.setPersonaId(personaIdNew);
      }
      telefono = em.merge(telefono);
      if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
        personaIdOld.getTelefonoList().remove(telefono);
        personaIdOld = em.merge(personaIdOld);
      }
      if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
        personaIdNew.getTelefonoList().add(telefono);
        personaIdNew = em.merge(personaIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Long id = telefono.getId();
        if (findTelefono(id) == null) {
          throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.");
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
      Telefono telefono;
      try {
        telefono = em.getReference(Telefono.class, id);
        telefono.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.", enfe);
      }
      Persona personaId = telefono.getPersonaId();
      if (personaId != null) {
        personaId.getTelefonoList().remove(telefono);
        personaId = em.merge(personaId);
      }
      em.remove(telefono);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Telefono> findTelefonoEntities() {
    return findTelefonoEntities(true, -1, -1);
  }

  public List<Telefono> findTelefonoEntities(int maxResults, int firstResult) {
    return findTelefonoEntities(false, maxResults, firstResult);
  }

  private List<Telefono> findTelefonoEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Telefono.class));
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

  public Telefono findTelefono(Long id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Telefono.class, id);
    } finally {
      em.close();
    }
  }

  public int getTelefonoCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Telefono> rt = cq.from(Telefono.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
