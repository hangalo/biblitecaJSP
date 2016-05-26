<%-- 
    Document   : editora_lista
    Created on : 26-mag-2016, 17.37.39
    Author     : Hangalo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.cpj.biblioteca.modelo.Editora"%>
<%@page import="com.cpj.biblioteca.dao.EditoraDAO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Biblioteca - Listagem dos autores</title>
        <meta name="description" content="Source code generated using layoutit.com">
        <meta name="author" content="Layout">

        <link href="bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/estilos.css" rel="stylesheet">
        <script src="js/jquery-1.12.3.min.js"></script>
        <script src="bootstrap-3.3.6/js/bootstrap.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
    </head>
</head>
<body>
    <div class="container">

        <h2>Lista das Editoras</h2>
        <%

            EditoraDAO editoraDAO = new EditoraDAO();
            List<Editora> editoras = editoraDAO.buscarTudo();

        %>
        <table class=" table table-hover">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Editora</th>
                    <th>N.º Casa</th>
                    <th>Rua</th>
                    <th>Bairro</th>
                    <th>Cx. Postal</th>
                    <th>Municipio</th>
                    <th>Tel. Fixo</th>
                    <th>Tel. Móvel</th>
                    <th>Email</th>
                    <th>Web Page</th>
                </tr>
            </thead>
            <tbody>
                <%for (Editora editora : editoras) {%>
                <tr>
                    <td><%=editora.getCodigo()%></td>
                    <td><%=editora.getNome()%></td>
                    <td><%=editora.getCasa()%></td>
                    <td><%=editora.getBairro()%></td>
                    <td><%=editora.getCaixaPostal()%></td>
                    <td><%=editora.getMunicipio()%></td>
                    <td><%=editora.getTelefoneFixo()%></td>
                    <td><%=editora.getTelefoneMovel()%></td>
                    <td><%=editora.getEmail()%></td>
                    <td><%=editora.getHomePage()%></td>


                    <td><a href="editoraControlo?accao=buscar_por_id&codigo=<%=editora.getCodigo()%>">Editar</a></td>
                    <td><a href="editoraControlo?accao=eliminar&input_codigo=<%=editora.getCodigo()%>">Eliminar</a></td>
                    <td><a href="editoraControlo?accao=ficha_editora&input_codigo=<%=editora.getCodigo()%>">Imprimir</a></td>
                </tr>
                <%}%>
            </tbody>
        </table>

        <p>
            <a class="btn btn-primary btn-large" href="editora_guardar.jsp">Nova Editora</a>
        </p>
        <p>
            <a class="btn btn-primary btn-large" href="index.html">Home</a>
        </p>
    </div>
</body>
</html>
