/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.CitaJpaController;
import com.controller.PersonaJpaController;
import com.dto.Cita;
import com.dto.Persona;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CitaEditServlet", urlPatterns = {"/CitaEditServlet"})
public class CitaEditServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    System.out.println("Entrando a Cita Edit Servlet");
//Obteniendo todos los parámetros que recibimos de la vista; solo para saber con qué variables llegan
    System.out.println(request.getParameterMap());
    for (Map.Entry<String, String[]> key : request.getParameterMap().entrySet()) {
      for (String value : key.getValue()) {
        System.out.println("Key: " + key.getKey() + " - Value: " + value);
      }
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
    try {
//      Inicialización de objetos
      CitaJpaController jpacCita = new CitaJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      
      Cita oldCita;
      Persona miCliente, miTecnico;

//      Lo relacionado a la fecha
      SimpleDateFormat sdf_fecha = new SimpleDateFormat("yyyy-MM-dd");
      Date fecha = sdf_fecha.parse(String.valueOf(request.getParameter("editFecha")));

      Date dt = new Date();
      Timestamp ts = new Timestamp(dt.getTime());
      System.out.println(ts);

//      Obteniendo el objeto en base al Id que nos da la vista
      miCliente = jpacPersona.findPersona(Long.valueOf(request.getParameter("editClienteId")));
      miTecnico =jpacPersona.findPersona(Long.valueOf(request.getParameter("editTecnicoId")));
      System.out.println("Personas obtenidas: " + miCliente + " - " + miTecnico);

      //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
      oldCita = jpacCita.findCita(Long.valueOf(request.getParameter("editId")));
      System.out.println("La Cita obtenida es: " + oldCita);

//      Comparando y asignando nuevos valores al objeto
      if (oldCita.getFecha() == null || oldCita.getFecha().compareTo(fecha) != 0) {
        oldCita.setFecha(fecha);
      }
      if (oldCita.getHora() == null || oldCita.getHora().compareTo(request.getParameter("editHora")) != 0) {
        oldCita.setHora(request.getParameter("editHora"));
      }
      if (!oldCita.getEstado().equals(request.getParameter("editEstado"))) {
        oldCita.setEstado(request.getParameter("editEstado"));
      }

      if (!oldCita.getClienteId().equals(miCliente)) {
        oldCita.setClienteId(miCliente);
      }
      if (!oldCita.getTecnicoId().equals(miTecnico)) {
        oldCita.setTecnicoId(miTecnico);
      }
      oldCita.setUpdatedAt(ts);
//      oldObject_distrito.setDireccionCollection(mi_lista_de_Direcciones);

      System.out.println("La Cita actualizada es: "
              + oldCita.getId() + ", " + oldCita.getFecha() + ", " + oldCita.getEstado() + ", " 
              + oldCita.getClienteId().getNombres() + ", " + oldCita.getTecnicoId().getNombres() + ", "
              + oldCita.getCreatedAt() + ", " + oldCita.getUpdatedAt());

      jpacCita.edit(oldCita);

      CitaListServlet call = new CitaListServlet();
      call.processRequest(request, response);

    } catch (Exception theException) {
      System.out.println(theException);
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
