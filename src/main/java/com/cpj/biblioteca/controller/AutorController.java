package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.service.AutorService;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.MunicipioService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chandimba
 */
public class AutorController extends HttpServlet {

    private String retorno;
    private AutorService autorService = new AutorService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            salvar(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            editar(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            buscarPeloCodigo(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            excluir(req, resp);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            String nomeDoAutor = request.getParameter("autor_nome");
            String sobrenomeDoAutor = request.getParameter("autor_sobrenome");
            Date dataDeNascimentoDoAutor = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("autor_datanascimento"));
            String biografiaDoAutor = request.getParameter("autor_biografia");
            Long codigoMunicipio = Long.parseLong(request.getParameter("autor_municipio"));
            
            Municipio municipio = new MunicipioService().buscarPeloCodigo(codigoMunicipio);

            Autor autor = new Autor();
            autor.setNome(nomeDoAutor);
            autor.setSobrenome(sobrenomeDoAutor);
            autor.setDataNascimento(dataDeNascimentoDoAutor);
            autor.setBiografia(biografiaDoAutor);
            autor.setMunicipio(municipio);

            retorno = autorService.salvar(autor);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de nascimento do autor";
        }

        sessao.setAttribute("autor_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigoDoAutor = Long.parseLong(request.getParameter("autor_codigo"));
            String nomeDoAutor = request.getParameter("autor_nome");
            String sobrenomeDoAutor = request.getParameter("autor_sobrenome");
            Date dataDeNascimentoDoAutor = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("autor_datanascimento"));
            String biografiaDoAutor = request.getParameter("autor_biografia");
            Long codigoMunicipio = Long.parseLong(request.getParameter("autor_municipio"));
            
            Municipio municipio = new MunicipioService().buscarPeloCodigo(codigoMunicipio);

            Autor autor = new Autor();
            autor.setCodigo(codigoDoAutor);
            autor.setNome(nomeDoAutor);
            autor.setSobrenome(sobrenomeDoAutor);
            autor.setDataNascimento(dataDeNascimentoDoAutor);
            autor.setBiografia(biografiaDoAutor);
            autor.setMunicipio(municipio);

            retorno = autorService.salvar(autor);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de nascimento do autor";
        }

        sessao.setAttribute("autor_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("categoria_codigo"));
            
            retorno = autorService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("categoria_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("autor_codigo"));
            
            Autor autor = autorService.buscarPeloCodigo(codigo);

            sessao.setAttribute("autor", autor);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("autor_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Autor> autores = autorService.buscarTudo();

            sessao.setAttribute("autores", autores);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("autor_mensagem", retorno);
        }

        response.sendRedirect("");
    }
}
