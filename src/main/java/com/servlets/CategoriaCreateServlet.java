package com.servlets;

import com.controller.CategoriaJpaController;
import com.dto.Categoria;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoriaCreateServlet", urlPatterns = {"/CategoriaCreateServlet"})
public class CategoriaCreateServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    
    System.out.println("Bandera Categoría Create Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      
      CategoriaJpaController jpacObject = new CategoriaJpaController(emf);
      Categoria categoria = new Categoria();
      
//      Date dt = new Date();
//      Timestamp ts = new Timestamp(dt.getTime());
//      System.out.println("La fecha obtenida es: " + ts);

//      Distrito mi_distrito = new Distrito();
//      mi_depa.setId(Long.valueOf(1));
//      mi_depa.setDepartamento("Arequipa");

//      mi_distrito.setIdTelefono(566);                        //No necesario, tiene auto_increment
      categoria.setUniqueId(String.valueOf(java.util.UUID.randomUUID()));
      categoria.setDescripcion(request.getParameter("addDescripcion"));
      categoria.setEstado("activo");
//      mi_objeto.setCreatedAt(ts);
//      mi_objeto.setUpdatedAt(ts);

      jpacObject.create(categoria);

      //Llamando al ListServlet
      CategoriaListServlet call = new CategoriaListServlet();
      call.processRequest(request, response);
//      response.sendRedirect("Distrito/List.jsp").forward(request, response);

    } catch (IOException | ServletException theException) {
      System.out.println(theException);
      System.out.println("Error"+e.toString());

    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
 
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }


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
