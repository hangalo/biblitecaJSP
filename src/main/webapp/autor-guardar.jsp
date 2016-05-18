<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- As 3 meta tags acima *devem* vir em primeiro lugar dentro do `head`; 
        qualquer outro conteúdo deve vir *após* essas tags -->
        <title>Registar novo autor</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
        <link href="jquery-ui-themes-1.11.4/themes/smoothness/jquery-ui.css" type="text/css" rel="stylesheet">
        <link href="css/estilos.css" type="text/css" rel="stylesheet">
        <!-- jQuery (obrigatório para plugins JavaScript do Bootstrap) -->
        <script src="jquery-ui-1.11.4/jquery-ui.js"></script>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- Inclui todos os plugins compilados (abaixo), 
        ou inclua ficheiros separadados se necessário -->
    </head>
    <body>       
        <h4 class="centrar">Registar novo autor</h4>
        <div class="container">
            <form class="form-horizontal" role="form">

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
                    <label class="col-xs-3 control-label">Municipio</label>
                    <div class="col-xs-5" >
                        <select class="form-control" id="selectMunicipio" name="selectMunicipio">
                            <option>1</option>
                            <option>2</option>
                        </select>

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">Breve Biografica:</label>
                    <div class="col-xs-8" >
                        <textarea class="form-control" rows="5" id="biografica"></textarea>

                    </div>
                </div>

                <div class="row row-centered">
                    
                <div class="col-md-3 col-centered">
                    
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    
                </div>
                  
                     <div class="col-md-3 col-centered">
                    <button type="submit" class="btn btn-primary">Cancelar</button>
                </div>
                    
                </div>


            </form>
        </div>
    </body>
</html>
