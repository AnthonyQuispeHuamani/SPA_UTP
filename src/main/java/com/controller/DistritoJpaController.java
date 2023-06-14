package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Direccion;
import com.dto.Distrito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DistritoJpaController implements Serializable {

  public DistritoJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Distrito distrito) {
    if (distrito.getDireccionList() == null) {
      distrito.setDireccionList(new ArrayList<Direccion>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
      for (Direccion direccionListDireccionToAttach : distrito.getDireccionList()) {
        direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getId());
        attachedDireccionList.add(direccionListDireccionToAttach);
      }
      distrito.setDireccionList(attachedDireccionList);
      em.persist(distrito);
      for (Direccion direccionListDireccion : distrito.getDireccionList()) {
        Distrito oldDistritoIdOfDireccionListDireccion = direccionListDireccion.getDistritoId();
        direccionListDireccion.setDistritoId(distrito);
        direccionListDireccion = em.merge(direccionListDireccion);
        if (oldDistritoIdOfDireccionListDireccion != null) {
          oldDistritoIdOfDireccionListDireccion.getDireccionList().remove(direccionListDireccion);
          oldDistritoIdOfDireccionListDireccion = em.merge(oldDistritoIdOfDireccionListDireccion);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Distrito distrito) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Distrito persistentDistrito = em.find(Distrito.class, distrito.getId());
      List<Direccion> direccionListOld = persistentDistrito.getDireccionList();
      List<Direccion> direccionListNew = distrito.getDireccionList();
      List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
      for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
        direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getId());
        attachedDireccionListNew.add(direccionListNewDireccionToAttach);
      }
      direccionListNew = attachedDireccionListNew;
      distrito.setDireccionList(direccionListNew);
      distrito = em.merge(distrito);
      for (Direccion direccionListOldDireccion : direccionListOld) {
        if (!direccionListNew.contains(direccionListOldDireccion)) {
          direccionListOldDireccion.setDistritoId(null);
          direccionListOldDireccion = em.merge(direccionListOldDireccion);
        }
      }
      for (Direccion direccionListNewDireccion : direccionListNew) {
        if (!direccionListOld.contains(direccionListNewDireccion)) {
          Distrito oldDistritoIdOfDireccionListNewDireccion = direccionListNewDireccion.getDistritoId();
          direccionListNewDireccion.setDistritoId(distrito);
          direccionListNewDireccion = em.merge(direccionListNewDireccion);
          if (oldDistritoIdOfDireccionListNewDireccion != null && !oldDistritoIdOfDireccionListNewDireccion.equals(distrito)) {
            oldDistritoIdOfDireccionListNewDireccion.getDireccionList().remove(direccionListNewDireccion);
            oldDistritoIdOfDireccionListNewDireccion = em.merge(oldDistritoIdOfDireccionListNewDireccion);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Long id = distrito.getId();
        if (findDistrito(id) == null) {
          throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.");
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
      Distrito distrito;
      try {
        distrito = em.getReference(Distrito.class, id);
        distrito.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.", enfe);
      }
      List<Direccion> direccionList = distrito.getDireccionList();
      for (Direccion direccionListDireccion : direccionList) {
        direccionListDireccion.setDistritoId(null);
        direccionListDireccion = em.merge(direccionListDireccion);
      }
      em.remove(distrito);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Distrito> findDistritoEntities() {
    return findDistritoEntities(true, -1, -1);
  }

  public List<Distrito> findDistritoEntities(int maxResults, int firstResult) {
    return findDistritoEntities(false, maxResults, firstResult);
  }

  private List<Distrito> findDistritoEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Distrito.class));
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

  public Distrito findDistrito(Long id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Distrito.class, id);
    } finally {
      em.close();
    }
  }

  public int getDistritoCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Distrito> rt = cq.from(Distrito.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
