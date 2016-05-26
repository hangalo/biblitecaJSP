<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.cpj.biblioteca.modelo.Municipio"%>
<%@page import="com.cpj.biblioteca.dao.MunicipioDAO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- As 3 meta tags acima *devem* vir em primeiro lugar dentro do `head`; 
        qualquer outro conteúdo deve vir *após* essas tags -->
        <title>Registar novo autor</title>

        <meta name="description" content="Source code generated using layoutit.com">
        <meta name="author" content="Layout">

        <link href="bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/estilos.css" rel="stylesheet">
        <script src="js/jquery-1.12.3.min.js"></script>
        <script src="bootstrap-3.3.6/js/bootstrap.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
    </head>
    <body>       
        <h4 class="centrar">Registar novo autor</h4>
        <div class="container">
            <form class="form-horizontal" role="form" action="autorControlo?accao=guardar" method="POST">

                <div class="form-group">
                    <label class="col-xs-3 control-label">Nome Completo:</label>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" name="nome" placeholder="Nome"/>
                    </div>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" name="sobrenome" placeholder="Sobrenome"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">Data de Nascimento:</label>
                    <div class="col-xs-5" >
                        <input type="text" data-provide="datepicker" class="form-control" id="data_nascimento" name="data_nascimento"/>

                    </div>
                </div>

                <div class="form-group">
                    <%
                        MunicipioDAO municipioDAO = new MunicipioDAO();
                        List<Municipio> municipios = municipioDAO.buscarTudo();

                    %>
                    <label class="col-xs-3 control-label">Municipio</label>
                    <div class="col-xs-5" >
                        <select class="form-control" id="selectMunicipio" name="selectMunicipio">

                            <%                                for (Municipio municipio : municipios) {%>
                            <option value="<%=municipio.getCodigo()%>"><%=municipio.getNome()%></option>
                            <%
                                }
                            %>


                        </select>

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">Breve Biografica:</label>
                    <div class="col-xs-8" >
                        <textarea class="form-control" rows="5" id="biografica" name="biografica"></textarea>

                    </div>
                </div>

                <div class="row row-centered">
                    <div class="col-md-3 col-centered">
                        <a class="btn btn-primary btn-large" href="index.html">Home</a>

                    </div>

                    <div class="col-md-3 col-centered">

                        <button type="submit" class="btn btn-primary" >Guardar</button>

                    </div>


                </div>


            </form>
        </div>
    </body>
</html>
