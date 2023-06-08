/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.DireccionJpaController;
import com.controller.PersonaJpaController;
import com.controller.TelefonoJpaController;
import com.dto.Direccion;
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

@WebServlet(name = "PersonaDestroyServlet", urlPatterns = {"/PersonaDestroyServlet"})
public class PersonaDestroyServlet extends HttpServlet {

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

    System.out.println("Entrando a Persona Destroy Servlet");
    System.out.println(request.getParameter("destroyId"));
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
//      TelefonoJpaController jpacTelefono = new TelefonoJpaController(emf);
//      DireccionJpaController jpacDireccion = new DireccionJpaController(emf);
      Persona objetoArchivado;
//      List<Telefono> miTelefono = new ArrayList<>();
//      List<Direccion> miDireccion = new ArrayList<>();

      objetoArchivado = jpacPersona.findPersona(Long.valueOf(request.getParameter("destroyId")));
      System.out.println(objetoArchivado.getDireccionList());

//      miTelefono = (List) objetoArchivado.getTelefonoCollection();
//      miDireccion = (List) objetoArchivado.getDireccionCollection();
//      
//      for (Telefono tel : miTelefono){
//        System.out.println(tel.getDescripcion());
//        tel.setEstado("eliminado");
//        jpacTelefono.edit(tel);
//      }
//      for (Direccion dir : miDireccion) {
//        System.out.println(dir.getDescripcion());
//        dir.setEstado("eliminado");
//        jpacDireccion.edit(dir);
//      }
      objetoArchivado.setEstado("eliminado");
      jpacPersona.edit(objetoArchivado);

      PersonaListServlet call = new PersonaListServlet();
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
