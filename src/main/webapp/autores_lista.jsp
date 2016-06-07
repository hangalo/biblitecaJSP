<%-- 
    Document   : cliente_lista
    Created on : 19-mag-2016, 1.21.44
    Author     : praveen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.cpj.biblioteca.modelo.Autor"%>
<%@page import="com.cpj.biblioteca.dao.AutorDAO"%>
<%@page import="java.util.List"%>
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

        <h2>Lista dos Autores</h2>
        <%

            AutorDAO autorDAO = new AutorDAO();

            List<Autor> autores = autorDAO.buscarTudo();

        %>
        <table class=" table table-hover">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>Sobrenome</th>
                    <th>Nascimento</th>
                    <th>Municipio</th>
                    <th>Breve Biografica</th>
                </tr>
            </thead>
            <tbody>
                <%for (Autor autor : autores) {%>
                <tr>
                    <td><%=autor.getCodigo()%></td>
                    <td><%=autor.getNome()%></td>
                    <td><%=autor.getSobrenome()%></td>
                    <td><%=autor.getDataNascimento()%></td>
                    <td><%=autor.getMunicipio()%></td>
                    <td><%=autor.getBiografia()%></td>

                    <td><a href="autorControlo?accao=buscar_por_id&codigo=<%=autor.getCodigo()%>">Editar</a></td>
                    <td><a href="autorControlo?accao=eliminar&codigo=<%=autor.getCodigo()%>">Eliminar</a></td>
                    <td><a href="autorControlo?accao=ficha_autor&codigo=<%=autor.getCodigo()%>">Imprimir</a></td>
                </tr>
                <%}%>
            </tbody>
        </table>

        <p>
            <a class="btn btn-primary btn-large" href="autor_guardar.jsp">Novo Autor</a>
        </p>
        <p>
            <a class="btn btn-primary btn-large" href="index.html">Home</a>
        </p>
    </div>
</body>
</html>
