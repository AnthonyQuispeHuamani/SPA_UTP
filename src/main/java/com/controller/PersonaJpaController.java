package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.TipoPersona;
import com.dto.Comprobante;
import java.util.ArrayList;
import java.util.List;
import com.dto.Direccion;
import com.dto.Telefono;
import com.dto.Cita;
import com.dto.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PersonaJpaController implements Serializable {

  public PersonaJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Persona persona) {
    if (persona.getComprobanteList() == null) {
      persona.setComprobanteList(new ArrayList<Comprobante>());
    }
    if (persona.getDireccionList() == null) {
      persona.setDireccionList(new ArrayList<Direccion>());
    }
    if (persona.getTelefonoList() == null) {
      persona.setTelefonoList(new ArrayList<Telefono>());
    }
    if (persona.getCitaList() == null) {
      persona.setCitaList(new ArrayList<Cita>());
    }
    if (persona.getCitaList1() == null) {
      persona.setCitaList1(new ArrayList<Cita>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      TipoPersona tipoPersonaId = persona.getTipoPersonaId();
      if (tipoPersonaId != null) {
        tipoPersonaId = em.getReference(tipoPersonaId.getClass(), tipoPersonaId.getId());
        persona.setTipoPersonaId(tipoPersonaId);
      }
      List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
      for (Comprobante comprobanteListComprobanteToAttach : persona.getComprobanteList()) {
        comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getId());
        attachedComprobanteList.add(comprobanteListComprobanteToAttach);
      }
      persona.setComprobanteList(attachedComprobanteList);
      List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
      for (Direccion direccionListDireccionToAttach : persona.getDireccionList()) {
        direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getId());
        attachedDireccionList.add(direccionListDireccionToAttach);
      }
      persona.setDireccionList(attachedDireccionList);
      List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
      for (Telefono telefonoListTelefonoToAttach : persona.getTelefonoList()) {
        telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getId());
        attachedTelefonoList.add(telefonoListTelefonoToAttach);
      }
      persona.setTelefonoList(attachedTelefonoList);
      List<Cita> attachedCitaList = new ArrayList<Cita>();
      for (Cita citaListCitaToAttach : persona.getCitaList()) {
        citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getId());
        attachedCitaList.add(citaListCitaToAttach);
      }
      persona.setCitaList(attachedCitaList);
      List<Cita> attachedCitaList1 = new ArrayList<Cita>();
      for (Cita citaList1CitaToAttach : persona.getCitaList1()) {
        citaList1CitaToAttach = em.getReference(citaList1CitaToAttach.getClass(), citaList1CitaToAttach.getId());
        attachedCitaList1.add(citaList1CitaToAttach);
      }
      persona.setCitaList1(attachedCitaList1);
      em.persist(persona);
      if (tipoPersonaId != null) {
        tipoPersonaId.getPersonaList().add(persona);
        tipoPersonaId = em.merge(tipoPersonaId);
      }
      for (Comprobante comprobanteListComprobante : persona.getComprobanteList()) {
        Persona oldPersonaIdOfComprobanteListComprobante = comprobanteListComprobante.getPersonaId();
        comprobanteListComprobante.setPersonaId(persona);
        comprobanteListComprobante = em.merge(comprobanteListComprobante);
        if (oldPersonaIdOfComprobanteListComprobante != null) {
          oldPersonaIdOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
          oldPersonaIdOfComprobanteListComprobante = em.merge(oldPersonaIdOfComprobanteListComprobante);
        }
      }
      for (Direccion direccionListDireccion : persona.getDireccionList()) {
        Persona oldPersonaIdOfDireccionListDireccion = direccionListDireccion.getPersonaId();
        direccionListDireccion.setPersonaId(persona);
        direccionListDireccion = em.merge(direccionListDireccion);
        if (oldPersonaIdOfDireccionListDireccion != null) {
          oldPersonaIdOfDireccionListDireccion.getDireccionList().remove(direccionListDireccion);
          oldPersonaIdOfDireccionListDireccion = em.merge(oldPersonaIdOfDireccionListDireccion);
        }
      }
      for (Telefono telefonoListTelefono : persona.getTelefonoList()) {
        Persona oldPersonaIdOfTelefonoListTelefono = telefonoListTelefono.getPersonaId();
        telefonoListTelefono.setPersonaId(persona);
        telefonoListTelefono = em.merge(telefonoListTelefono);
        if (oldPersonaIdOfTelefonoListTelefono != null) {
          oldPersonaIdOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
          oldPersonaIdOfTelefonoListTelefono = em.merge(oldPersonaIdOfTelefonoListTelefono);
        }
      }
      for (Cita citaListCita : persona.getCitaList()) {
        Persona oldClienteIdOfCitaListCita = citaListCita.getClienteId();
        citaListCita.setClienteId(persona);
        citaListCita = em.merge(citaListCita);
        if (oldClienteIdOfCitaListCita != null) {
          oldClienteIdOfCitaListCita.getCitaList().remove(citaListCita);
          oldClienteIdOfCitaListCita = em.merge(oldClienteIdOfCitaListCita);
        }
      }
      for (Cita citaList1Cita : persona.getCitaList1()) {
        Persona oldTecnicoIdOfCitaList1Cita = citaList1Cita.getTecnicoId();
        citaList1Cita.setTecnicoId(persona);
        citaList1Cita = em.merge(citaList1Cita);
        if (oldTecnicoIdOfCitaList1Cita != null) {
          oldTecnicoIdOfCitaList1Cita.getCitaList1().remove(citaList1Cita);
          oldTecnicoIdOfCitaList1Cita = em.merge(oldTecnicoIdOfCitaList1Cita);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Persona persona) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Persona persistentPersona = em.find(Persona.class, persona.getId());
      TipoPersona tipoPersonaIdOld = persistentPersona.getTipoPersonaId();
      TipoPersona tipoPersonaIdNew = persona.getTipoPersonaId();
      List<Comprobante> comprobanteListOld = persistentPersona.getComprobanteList();
      List<Comprobante> comprobanteListNew = persona.getComprobanteList();
      List<Direccion> direccionListOld = persistentPersona.getDireccionList();
      List<Direccion> direccionListNew = persona.getDireccionList();
      List<Telefono> telefonoListOld = persistentPersona.getTelefonoList();
      List<Telefono> telefonoListNew = persona.getTelefonoList();
      List<Cita> citaListOld = persistentPersona.getCitaList();
      List<Cita> citaListNew = persona.getCitaList();
      List<Cita> citaList1Old = persistentPersona.getCitaList1();
      List<Cita> citaList1New = persona.getCitaList1();
      if (tipoPersonaIdNew != null) {
        tipoPersonaIdNew = em.getReference(tipoPersonaIdNew.getClass(), tipoPersonaIdNew.getId());
        persona.setTipoPersonaId(tipoPersonaIdNew);
      }
      List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
      for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
        comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getId());
        attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
      }
      comprobanteListNew = attachedComprobanteListNew;
      persona.setComprobanteList(comprobanteListNew);
      List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
      for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
        direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getId());
        attachedDireccionListNew.add(direccionListNewDireccionToAttach);
      }
      direccionListNew = attachedDireccionListNew;
      persona.setDireccionList(direccionListNew);
      List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
      for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
        telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getId());
        attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
      }
      telefonoListNew = attachedTelefonoListNew;
      persona.setTelefonoList(telefonoListNew);
      List<Cita> attachedCitaListNew = new ArrayList<Cita>();
      for (Cita citaListNewCitaToAttach : citaListNew) {
        citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getId());
        attachedCitaListNew.add(citaListNewCitaToAttach);
      }
      citaListNew = attachedCitaListNew;
      persona.setCitaList(citaListNew);
      List<Cita> attachedCitaList1New = new ArrayList<Cita>();
      for (Cita citaList1NewCitaToAttach : citaList1New) {
        citaList1NewCitaToAttach = em.getReference(citaList1NewCitaToAttach.getClass(), citaList1NewCitaToAttach.getId());
        attachedCitaList1New.add(citaList1NewCitaToAttach);
      }
      citaList1New = attachedCitaList1New;
      persona.setCitaList1(citaList1New);
      persona = em.merge(persona);
      if (tipoPersonaIdOld != null && !tipoPersonaIdOld.equals(tipoPersonaIdNew)) {
        tipoPersonaIdOld.getPersonaList().remove(persona);
        tipoPersonaIdOld = em.merge(tipoPersonaIdOld);
      }
      if (tipoPersonaIdNew != null && !tipoPersonaIdNew.equals(tipoPersonaIdOld)) {
        tipoPersonaIdNew.getPersonaList().add(persona);
        tipoPersonaIdNew = em.merge(tipoPersonaIdNew);
      }
      for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
        if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
          comprobanteListOldComprobante.setPersonaId(null);
          comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
        }
      }
      for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
        if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
          Persona oldPersonaIdOfComprobanteListNewComprobante = comprobanteListNewComprobante.getPersonaId();
          comprobanteListNewComprobante.setPersonaId(persona);
          comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
          if (oldPersonaIdOfComprobanteListNewComprobante != null && !oldPersonaIdOfComprobanteListNewComprobante.equals(persona)) {
            oldPersonaIdOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
            oldPersonaIdOfComprobanteListNewComprobante = em.merge(oldPersonaIdOfComprobanteListNewComprobante);
          }
        }
      }
      for (Direccion direccionListOldDireccion : direccionListOld) {
        if (!direccionListNew.contains(direccionListOldDireccion)) {
          direccionListOldDireccion.setPersonaId(null);
          direccionListOldDireccion = em.merge(direccionListOldDireccion);
        }
      }
      for (Direccion direccionListNewDireccion : direccionListNew) {
        if (!direccionListOld.contains(direccionListNewDireccion)) {
          Persona oldPersonaIdOfDireccionListNewDireccion = direccionListNewDireccion.getPersonaId();
          direccionListNewDireccion.setPersonaId(persona);
          direccionListNewDireccion = em.merge(direccionListNewDireccion);
          if (oldPersonaIdOfDireccionListNewDireccion != null && !oldPersonaIdOfDireccionListNewDireccion.equals(persona)) {
            oldPersonaIdOfDireccionListNewDireccion.getDireccionList().remove(direccionListNewDireccion);
            oldPersonaIdOfDireccionListNewDireccion = em.merge(oldPersonaIdOfDireccionListNewDireccion);
          }
        }
      }
      for (Telefono telefonoListOldTelefono : telefonoListOld) {
        if (!telefonoListNew.contains(telefonoListOldTelefono)) {
          telefonoListOldTelefono.setPersonaId(null);
          telefonoListOldTelefono = em.merge(telefonoListOldTelefono);
        }
      }
      for (Telefono telefonoListNewTelefono : telefonoListNew) {
        if (!telefonoListOld.contains(telefonoListNewTelefono)) {
          Persona oldPersonaIdOfTelefonoListNewTelefono = telefonoListNewTelefono.getPersonaId();
          telefonoListNewTelefono.setPersonaId(persona);
          telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
          if (oldPersonaIdOfTelefonoListNewTelefono != null && !oldPersonaIdOfTelefonoListNewTelefono.equals(persona)) {
            oldPersonaIdOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
            oldPersonaIdOfTelefonoListNewTelefono = em.merge(oldPersonaIdOfTelefonoListNewTelefono);
          }
        }
      }
      for (Cita citaListOldCita : citaListOld) {
        if (!citaListNew.contains(citaListOldCita)) {
          citaListOldCita.setClienteId(null);
          citaListOldCita = em.merge(citaListOldCita);
        }
      }
      for (Cita citaListNewCita : citaListNew) {
        if (!citaListOld.contains(citaListNewCita)) {
          Persona oldClienteIdOfCitaListNewCita = citaListNewCita.getClienteId();
          citaListNewCita.setClienteId(persona);
          citaListNewCita = em.merge(citaListNewCita);
          if (oldClienteIdOfCitaListNewCita != null && !oldClienteIdOfCitaListNewCita.equals(persona)) {
            oldClienteIdOfCitaListNewCita.getCitaList().remove(citaListNewCita);
            oldClienteIdOfCitaListNewCita = em.merge(oldClienteIdOfCitaListNewCita);
          }
        }
      }
      for (Cita citaList1OldCita : citaList1Old) {
        if (!citaList1New.contains(citaList1OldCita)) {
          citaList1OldCita.setTecnicoId(null);
          citaList1OldCita = em.merge(citaList1OldCita);
        }
      }
      for (Cita citaList1NewCita : citaList1New) {
        if (!citaList1Old.contains(citaList1NewCita)) {
          Persona oldTecnicoIdOfCitaList1NewCita = citaList1NewCita.getTecnicoId();
          citaList1NewCita.setTecnicoId(persona);
          citaList1NewCita = em.merge(citaList1NewCita);
          if (oldTecnicoIdOfCitaList1NewCita != null && !oldTecnicoIdOfCitaList1NewCita.equals(persona)) {
            oldTecnicoIdOfCitaList1NewCita.getCitaList1().remove(citaList1NewCita);
            oldTecnicoIdOfCitaList1NewCita = em.merge(oldTecnicoIdOfCitaList1NewCita);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Long id = persona.getId();
        if (findPersona(id) == null) {
          throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
      Persona persona;
      try {
        persona = em.getReference(Persona.class, id);
        persona.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
      }
      TipoPersona tipoPersonaId = persona.getTipoPersonaId();
      if (tipoPersonaId != null) {
        tipoPersonaId.getPersonaList().remove(persona);
        tipoPersonaId = em.merge(tipoPersonaId);
      }
      List<Comprobante> comprobanteList = persona.getComprobanteList();
      for (Comprobante comprobanteListComprobante : comprobanteList) {
        comprobanteListComprobante.setPersonaId(null);
        comprobanteListComprobante = em.merge(comprobanteListComprobante);
      }
      List<Direccion> direccionList = persona.getDireccionList();
      for (Direccion direccionListDireccion : direccionList) {
        direccionListDireccion.setPersonaId(null);
        direccionListDireccion = em.merge(direccionListDireccion);
      }
      List<Telefono> telefonoList = persona.getTelefonoList();
      for (Telefono telefonoListTelefono : telefonoList) {
        telefonoListTelefono.setPersonaId(null);
        telefonoListTelefono = em.merge(telefonoListTelefono);
      }
      List<Cita> citaList = persona.getCitaList();
      for (Cita citaListCita : citaList) {
        citaListCita.setClienteId(null);
        citaListCita = em.merge(citaListCita);
      }
      List<Cita> citaList1 = persona.getCitaList1();
      for (Cita citaList1Cita : citaList1) {
        citaList1Cita.setTecnicoId(null);
        citaList1Cita = em.merge(citaList1Cita);
      }
      em.remove(persona);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Persona> findPersonaEntities() {
    return findPersonaEntities(true, -1, -1);
  }

  public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
    return findPersonaEntities(false, maxResults, firstResult);
  }

  private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Persona.class));
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

  public Persona findPersona(Long id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Persona.class, id);
    } finally {
      em.close();
    }
  }

  public int getPersonaCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Persona> rt = cq.from(Persona.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
