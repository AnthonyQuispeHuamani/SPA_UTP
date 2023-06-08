<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar Teléfonos">
  <jsp:attribute name="head_area">
  </jsp:attribute>
  <jsp:attribute name="body_area">



    <div class="col-md-12">
      <div class="card">
        <div class="card-header">
          <div class="d-flex align-items-center">
            <h4 class="card-title">Teléfonos</h4>
            <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
              <i class="fa fa-plus"></i>
              Añadir Teléfono
            </button>
          </div>
        </div>
        <div class="card-body">

          <!-- Modal Crear -->
          <div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header no-bd">
                  <h5 class="modal-title">
                    <span class="fw-mediumbold">
                      Nuevo</span> 
                    <span class="fw-light">
                      Teléfono
                    </span>
                  </h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <form  action="TelefonoCreateServlet" method="post">
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group form-group-default">
                          <label>Número</label>
                          <input required name="addDescripcion" type="text" class="form-control">
                        </div>
                        <div class="form-group form-group-default">
                          <label>Persona</label>
                          <c:choose>
                            <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                              <select class="form-control" name="addPersonaId">
                                <c:forEach var="temp" items="${mi_lista_de_personas}">
                                  <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                </c:forEach>
                              </select>
                            </c:when>
                            <c:otherwise>
                              <input readonly name="addPersonaId" type="text" class="form-control" value="${miPersonaObtenida.id}">
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                      </div>
                    </div>
                  </form>
                </div>

              </div>
            </div>
          </div>

          <div class="table-responsive">
            <table id="add-row" class="display table table-striped table-hover" >
              <thead>
                <tr>
                  <th>Número</th>
                  <th>Persona</th>
                  <th>Estado</th>
                  <th>Creado</th>
                  <th>Modificado</th>
                  <th style="width: 10%">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="temp" items="${mi_lista_de_telefonos }">
                  <c:choose>
                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                      <tr>
                        <td>${temp.descripcion }</td>
                        <td>${temp.personaId.nombres}</td>
                        <td>${temp.estado}</td>
                          <td>
                            <fmt:formatDate value="${temp.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                          </td>
                          <td>
                            <fmt:formatDate value="${temp.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                          </td>
                        <td>
                          <div class="form-button-action">
                            <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                    data-target="#${temp.uniqueId}">
                              <i class="fa fa-edit"></i>
                            </button>
                            <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                    data-target="#${temp.id}${temp.uniqueId}">
                              <i class="fa fa-times"></i>
                            </button>
                          </div>
                        </td>
                      </tr>
                    </c:when>
                    <c:otherwise>
                      <c:if test="${temp.personaId.nombres.equalsIgnoreCase(miPersonaObtenida.nombres)}">
                        <tr>
                          <td>${temp.descripcion }</td>
                          <td>${temp.personaId.nombres}</td>
                          <td>${temp.estado}</td>
                          <td>
                            <fmt:formatDate value="${temp.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                          </td>
                          <td>
                            <fmt:formatDate value="${temp.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                          </td>
                          <td>
                            <div class="form-button-action">
                              <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                      data-target="#${temp.uniqueId}">
                                <i class="fa fa-edit"></i>
                              </button>
                              <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                      data-target="#${temp.id}${temp.uniqueId}">
                                <i class="fa fa-times"></i>
                              </button>
                            </div>
                          </td>
                        </tr>
                      </c:if>
                    </c:otherwise>
                  </c:choose>



                  <!-- Modal Eliminar -->
                <div class="modal fade" id="${temp.id}${temp.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header no-bd">
                        <h5 class="modal-title">
                          <span class="fw-light">¿Está relamente seguro de querer</span>
                          <span class="fw-mediumbold"> eliminar </span>
                          <span class="fw-light">este teléfono?</span>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <form  action="TelefonoDestroyServlet" method="post">
                          <div class="row">
                            <div class="col-sm-12">
                              <div class="form-group form-group-default">
                                <label>Id</label>
                                <input name="destroyId" type="text" class="form-control" value="${temp.id }" readonly>
                              </div>
                              <div class="form-group form-group-default">
                                <label>Número</label>
                                <input name="destroyObject" type="text" class="form-control" value="${temp.descripcion }" readonly>
                              </div>

                            </div>
                            <div class="col-md-6">
                              <button type="submit" class="btn btn-danger">Borrar</button>
                            </div>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Modal Editar -->
                <div class="modal fade" id="${temp.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header no-bd">
                        <h5 class="modal-title">
                          <span class="fw-mediumbold">
                            Editar</span> 
                          <span class="fw-light">
                            Número
                          </span>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <form  action="TelefonoEditServlet" method="post">
                          <div class="row">
                            <div class="col-sm-12">
                              <div class="form-group form-group-default">
                                <label>Id</label>
                                <input name="editId" type="text" class="form-control" value="${temp.id }" readonly>
                              </div>
                              <div class="form-group form-group-default">
                                <label>Número</label>
                                <input required name="editDescripcion" type="text" class="form-control" value="${temp.descripcion }">
                              </div>

                              <div class="form-group form-group-default">
                                <label>Persona</label>
                                <c:choose>
                                  <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                                    <select class="form-control" name="editPersonaId">
                                      <c:forEach var="tempEdit" items="${mi_lista_de_personas }">
                                        <option value="${tempEdit.id }">${tempEdit.nombres }</option>
                                      </c:forEach>
                                    </select>
                                  </c:when>
                                  <c:otherwise>
                                    <input readonly name="editPersonaId" type="text" class="form-control" value="${miPersonaObtenida.id}">
                                  </c:otherwise>
                                </c:choose>

                              </div>
                              <div class="form-group form-group-default">
                                <label>Estado</label>
                                <select class="form-control" name="editEstado" id="editEstado">
                                  <option value="activo">Activo</option>
                                  <option value="inactivo">Inactivo</option>
                                  <!--<option value="eliminado">Eliminado</option>-->
                                </select>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery.3.2.1.min.js"></script>
    <!-- Datatables -->
    <script src="assets/js/plugin/datatables/datatables.min.js"></script>
    <script>
      // Add Row
      $('#add-row').DataTable({
        "pageLength": 5,
      });
    </script>

  </jsp:attribute>
</t:template>