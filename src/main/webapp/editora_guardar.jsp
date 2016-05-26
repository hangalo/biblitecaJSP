<%-- 
    Document   : editora_editar
    Created on : 26-mag-2016, 17.37.50
    Author     : praveen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.cpj.biblioteca.modelo.Municipio"%>
<%@page import="com.cpj.biblioteca.dao.MunicipioDAO"%>
<%@page import="com.cpj.biblioteca.modelo.Editora"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- As 3 meta tags acima *devem* vir em primeiro lugar dentro do `head`; 
        qualquer outro conteúdo deve vir *após* essas tags -->
        <title>Registar nova editora</title>

        <meta name="description" content="Source code generated using layoutit.com">
        <meta name="author" content="Layout">

        <link href="bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/estilos.css" rel="stylesheet">
        <script src="js/jquery-1.12.3.min.js"></script>
        <script src="bootstrap-3.3.6/js/bootstrap.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
    </head>
    <body>       
        <h4 class="centrar">Registar Nova Editora</h4>
        
        <div class="container">
            <form class="form-horizontal" role="form" action="editoraControlo?accao=guardar" method="POST">
                
                
                 <div class="form-group">
                    <label class="col-xs-3 control-label">Editora:</label>
                    <div class="col-xs-8" >
                        <input type="text" class="form-control" id="input_nome" name="input_nome" placeholder="Nome da Editora"/>

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">Endereço:</label>
                    <div class="col-xs-2">
                        <input type="text" class="form-control" name="input_casa" placeholder="N.º Casa"/>
                    </div>
                    <div class="col-xs-2">
                        <input type="text" class="form-control" name="input_rua" placeholder="Rua"/>
                    </div>
                    <div class="col-xs-4">
                        <input type="text" class="form-control" name="input_rua" placeholder="Bairro"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">Caixa Postal:</label>
                    <div class="col-xs-5" >
                        <input type="text"  class="form-control" id="input_caixa_postal" name="input_caixa_postal" placeholder="Caixa Postal"/>
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
                    <label class="col-xs-3 control-label">Telefone Fixo:</label>
                    <div class="col-xs-5" >
                        <input type="text"  class="form-control" id="input_telefone_fixo" name="input_telefone_fixo" placeholder="Telefone Fixo"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">Telefone Móvel:</label>
                    <div class="col-xs-5" >
                        <input type="text"  class="form-control" id="input_telefone_movel" name="input_telefone_movel" placeholder="Telefone Móvel"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">Email:</label>
                    <div class="col-xs-5" >
                        <input type="email"  class="form-control" id="input_email" name="input_email" placeholder="Email"/>
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-xs-3 control-label">Home Page:</label>
                    <div class="col-xs-5" >
                        <input type="text"  class="form-control" id="input_home_page" name="input_home_page" placeholder="Home Page"/>
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
