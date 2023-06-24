<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar Citas">
    <jsp:attribute name="head_area">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    </jsp:attribute>
    <jsp:attribute name="body_area">


        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Citas Programadas</h4>
                        <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                            <i class="fa fa-plus"></i>
                            Añadir Nueva Cita
                        </button>
                    </div>
                </div>
                <div class="card-body">

                    <!-- Modal Crear -->
                    <!--<div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">-->
                    <div class="modal fade" id="addRowModal" role="dialog" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header no-bd">
                                    <h5 class="modal-title">
                                        <span class="fw-mediumbold">
                                            Nueva</span>
                                        <span class="fw-light">
                                            Cita
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="CitaCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group form-group-default">
                                                    <label>Fecha Cita</label>
                                                    <input required name="addFecha" type="date" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Hora Cita</label>
                                                    <select class="form-control" name="addHora">
                                                        <option value="00hrs">ninguna</option>
                                                        <option value="07hrs">07hrs</option>
                                                        <option value="08hrs">08hrs</option>
                                                        <option value="09hrs">09hrs</option>
                                                        <option value="10hrs">10hrs</option>
                                                        <option value="11hrs">11hrs</option>
                                                        <option value="12hrs">12hrs</option>
                                                        <option value="13hrs">13hrs</option>
                                                        <option value="14hrs">14hrs</option>
                                                        <option value="15hrs">15hrs</option>
                                                        <option value="16hrs">16hrs</option>
                                                        <option value="17hrs">17hrs</option>
                                                        <option value="18hrs">18hrs</option>
                                                        <option value="19hrs">19hrs</option>
                                                        <option value="20hrs">20hrs</option>
                                                        <option value="21hrs">21hrs</option>
                                                        <option value="22hrs">22hrs</option>
                                                    </select>
                                                </div>

                                                <div class="form-group form-group-default">
                                                    <label>Cliente</label>
                                                    <select class="form-control js-example-basic-single" name="addClienteId" style="width: 100%">
                                                        <c:forEach var="temp" items="${mi_lista_de_personas}">
                                                            <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                                                                <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Técnico</label>
                                                    <select class="form-control" name="addTecnicoId">
                                                        <c:forEach var="temp" items="${mi_lista_de_personas}">
                                                            <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                                                                <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Servicio</label>
                                                    <select class="form-control" name="addServicioId">
                                                        <c:forEach var="tempC" items="${servicios}">
                                                            <option value="${tempC.id}">${tempC.descripcion}: ${tempC.minutos}min: S/${tempC.precio}</option>
                                                        </c:forEach>
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


                    <!-- Listar -->
                    <div class="table-responsive">
                        <table id="add-row" class="display table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Cliente</th>
                                    <th>Técnico</th>
                                    <th>Servicio</th>
                                    <!--<th>Placa</th>-->
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tempObjeto" items="${mi_lista_de_citas }">
                                    <tr>
                                        <td>${tempObjeto.fecha.getDate()}/${tempObjeto.fecha.getMonth()}</td>
                                        <td>${tempObjeto.hora}</td>
                                        <td>${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}</td>
                                        <td>${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}</td>
                                        <td>
                                            ${tempObjeto.servicioId.descripcion}<br>${tempObjeto.servicioId.minutos}min<br>S/${tempObjeto.servicioId.precio}
                                        </td>
                                        <td>${tempObjeto.estado}</td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium"
                                                            timeStyle="short" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium"
                                                            timeStyle="short" />
                                        </td>
                                        <td>
                                            <div class="form-button-action">
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                        data-target="#${tempObjeto.uniqueId}">
                                                    <i class="fa fa-edit"></i>
                                                </button>
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                        data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>

                                    <!-- Modal Eliminar -->
                                <div class="modal fade" id="${tempObjeto.id}${tempObjeto.uniqueId}" tabindex="-1"
                                     role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-light">¿Está relamente seguro de querer</span>
                                                    <span class="fw-mediumbold"> eliminar </span>
                                                    <span class="fw-light">esta Cita?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="CitaDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" type="text" class="form-control"
                                                                       value="${tempObjeto.id }" readonly>
                                                            </div>

                                                            <div class="form-group form-group-default">
                                                                <label>Fecha</label>
                                                                <input type="text" class="form-control"
                                                                       value="${tempObjeto.fecha.getDate()}/${tempObjeto.fecha.getMonth()}"
                                                                       readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Hora</label>
                                                                <input type="text" class="form-control" value="${tempObjeto.hora }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Cliente</label>
                                                                <input type="text" class="form-control"
                                                                       value="${tempObjeto.clienteId.nombres} - ${tempObjeto.clienteId.apellidos}"
                                                                       readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Técnico</label>
                                                                <input type="text" class="form-control"
                                                                       value="${tempObjeto.tecnicoId.nombres} - ${tempObjeto.tecnicoId.apellidos}"
                                                                       readonly>
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
                                <!--<div class="modal fade" id="${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">-->
                                <div class="modal fade" id="${tempObjeto.uniqueId}" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-mediumbold">
                                                        Editar</span>
                                                    <span class="fw-light">
                                                        Cita
                                                    </span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="CitaEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" id="editId" type="text" class="form-control"
                                                                       value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Fecha Cita</label>
                                                                <input required name="editFecha" type="date" class="form-control"
                                                                       value="${tempObjeto.fecha }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Hora Cita</label>
                                                                <select class="form-control" name="editHora">
                                                                    <option value="00hrs">ninguna</option>
                                                                    <option value="07hrs">07hrs</option>
                                                                    <option value="08hrs">08hrs</option>
                                                                    <option value="09hrs">09hrs</option>
                                                                    <option value="10hrs">10hrs</option>
                                                                    <option value="11hrs">11hrs</option>
                                                                    <option value="12hrs">12hrs</option>
                                                                    <option value="13hrs">13hrs</option>
                                                                    <option value="14hrs">14hrs</option>
                                                                    <option value="15hrs">15hrs</option>
                                                                    <option value="16hrs">16hrs</option>
                                                                    <option value="17hrs">17hrs</option>
                                                                    <option value="18hrs">18hrs</option>
                                                                    <option value="19hrs">19hrs</option>
                                                                    <option value="20hrs">20hrs</option>
                                                                    <option value="21hrs">21hrs</option>
                                                                    <option value="22hrs">22hrs</option>
                                                                </select>
                                                            </div>

                                                            <div class="form-group form-group-default">
                                                                <label>Cliente</label>
                                                                <select class="form-control js-example-basic-single" name="editClienteId" style="width: 100%">
                                                                    <c:forEach var="temp" items="${mi_lista_de_personas}">
                                                                        <c:if
                                                                            test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                                                                            <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Técnico</label>
                                                                <select class="form-control" name="editTecnicoId">
                                                                    <c:forEach var="temp" items="${mi_lista_de_personas}">
                                                                        <c:if
                                                                            test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                                                                            <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Servicio</label>
                                                                <select class="form-control" name="editServicioId">
                                                                    <c:forEach var="temp" items="${servicios}">
                                                                        <option value="${temp.id}">${temp.descripcion}: ${temp.minutos}min:
                                                                            S/${temp.precio}</option>
                                                                        </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Estado</label>
                                                                <select class="form-control" name="editEstado">
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
        <script>
            $(document).ready(function () {
                $('.js-example-basic-single').select2();
            });
        </script>

        <!--Datatables--> 
        <script src="assets/js/plugin/datatables/datatables.min.js"></script>
        <script>
            // Add Row
            $('#add-row').DataTable({
                "pageLength": 5,
            });
        </script>

    </jsp:attribute>
</t:template>
