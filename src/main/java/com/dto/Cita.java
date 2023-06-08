/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author luciano
 */
@Entity
@AdditionalCriteria("this.estado <> 'eliminado'")
@Table(name = "cita")
@NamedQueries({
  @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c")})
public class Cita implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 36)
  @Column(name = "unique_id")
  private String uniqueId;
  @Column(name = "fecha")
  @Temporal(TemporalType.DATE)
  private Date fecha;
  @Size(max = 5)
  @Column(name = "hora")
  private String hora;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 9)
  @Column(name = "estado")
  private String estado;
  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;
  @JoinColumn(name = "cliente_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Persona clienteId;
  @JoinColumn(name = "servicio_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Servicio servicioId;
  @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Persona tecnicoId;

  public Cita() {
  }

  public Cita(Long id) {
    this.id = id;
  }

  public Cita(Long id, String uniqueId, String estado) {
    this.id = id;
    this.uniqueId = uniqueId;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getHora() {
    return hora;
  }

  public void setHora(String hora) {
    this.hora = hora;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Persona getClienteId() {
    return clienteId;
  }

  public void setClienteId(Persona clienteId) {
    this.clienteId = clienteId;
  }

  public Servicio getServicioId() {
    return servicioId;
  }

  public void setServicioId(Servicio servicioId) {
    this.servicioId = servicioId;
  }

  public Persona getTecnicoId() {
    return tecnicoId;
  }

  public void setTecnicoId(Persona tecnicoId) {
    this.tecnicoId = tecnicoId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Cita)) {
      return false;
    }
    Cita other = (Cita) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.dto.Cita[ id=" + id + " ]";
  }
  
}
