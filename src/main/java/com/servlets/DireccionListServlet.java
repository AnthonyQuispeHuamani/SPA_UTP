/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.DireccionJpaController;
import com.controller.DistritoJpaController;
import com.controller.PersonaJpaController;
import com.dto.Direccion;
import com.dto.Distrito;
import com.dto.Persona;
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

@WebServlet(name = "DireccionListServlet", urlPatterns = {"/DireccionListServlet"})
public class DireccionListServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

    System.out.println("Entrando a Dirección List Servlet");
    try {
      DireccionJpaController jpacDireccion = new DireccionJpaController(emf);
//      DireccionPersonaJpaController jpacDirPersona = new DireccionPersonaJpaController(emf);
      DistritoJpaController jpacDistrito = new DistritoJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      
      List<Direccion> direcciones = new ArrayList<>();
//      List<DireccionPersona> miListaDeDirPersonas = new ArrayList<>();
      List<Distrito> distritos = new ArrayList<>();
      List<Persona> personas = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      direcciones = jpacDireccion.findDireccionEntities();
      distritos = jpacDistrito.findDistritoEntities();
      personas = jpacPersona.findPersonaEntities();

      for (Direccion temp1 : direcciones) {
        System.out.println(temp1.getId() + " - " + temp1.getDescripcion());
      }
      for (Distrito temp2 : distritos) {
        System.out.println(temp2.getId() + " - " + temp2.getDescripcion());
      }

      
      
      request.setAttribute("mi_lista_de_direcciones", direcciones);
      request.setAttribute("mi_lista_de_distritos", distritos);
      request.setAttribute("miListaDePersonas", personas);
      request.getRequestDispatcher("Direccion.jsp").forward(request, response);

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
