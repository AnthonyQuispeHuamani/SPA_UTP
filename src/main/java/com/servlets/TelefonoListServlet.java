/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.PersonaJpaController;
import com.controller.TelefonoJpaController;
import com.dto.Persona;
import com.dto.Telefono;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TelefonoListServlet", urlPatterns = {"/TelefonoListServlet"})
public class TelefonoListServlet extends HttpServlet {

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

    System.out.println("Entrando a Telefono List Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

      TelefonoJpaController jpacTelefono = new TelefonoJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);

      List<Telefono> telefonos = new ArrayList<>();
      List<Persona> personas = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      telefonos = jpacTelefono.findTelefonoEntities();
      personas = jpacPersona.findPersonaEntities();

      for (Telefono temp1 : telefonos) {
        System.out.println(temp1.getId() + " - " + temp1.getDescripcion());
      }
      for (Persona temp2 : personas) {
        System.out.println(temp2.getId() + " - " + temp2.getNombres());
      }

      request.setAttribute("mi_lista_de_telefonos", telefonos);
      request.setAttribute("mi_lista_de_personas", personas);
      request.getRequestDispatcher("Telefono.jsp").forward(request, response);

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
