/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.CitaJpaController;
import com.controller.PersonaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Cita;
import com.dto.Persona;
import com.dto.Servicio;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CitaListServlet", urlPatterns = {"/CitaListServlet"})
public class CitaListServlet extends HttpServlet {

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
    System.out.println("Entrando al List Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      
      CitaJpaController jpacCita = new CitaJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      ServicioJpaController jpacServicio = new ServicioJpaController(emf);

      List<Cita> citas = new ArrayList<>();
      List<Persona> personas = new ArrayList<>();
      List<Servicio> servicios = new ArrayList<>();


//      System.out.println(jpacontroller_object.findDistritoEntities());
      citas = jpacCita.findCitaEntities();
      personas = jpacPersona.findPersonaEntities();
      servicios = jpacServicio.findServicioEntities();

      
      DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
      
      for (Cita c : citas) {
        System.out.println(c.getId() + ":" + c.getServicioId().getDetalles()+ ":" +
                c.getFecha().getDate()+"/"+c.getFecha().toString() +"-----"+ "Fecha: " + df.format(c.getFecha()));
      }
      for (Persona p : personas) {
        System.out.println(p.getId() + " - " + p.getNombres());
      }
      

      request.setAttribute("mi_lista_de_citas", citas);
      request.setAttribute("mi_lista_de_personas", personas);
      request.setAttribute("servicios", servicios);
      //request.getRequestDispatcher("/SendEmailServlet").include(request, response);
      request.getRequestDispatcher("Cita.jsp").forward(request, response);

    } catch (IOException | ServletException theException) {
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
