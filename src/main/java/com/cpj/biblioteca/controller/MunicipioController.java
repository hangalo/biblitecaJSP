package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.modelo.Provincia;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.MunicipioService;
import com.cpj.biblioteca.service.ProvinciaService;
import java.io.IOException;
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
public class MunicipioController extends HttpServlet {

    private String retorno;
    private MunicipioService municipioService = new MunicipioService();

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
            String nomeDoMunicipio = request.getParameter("municipio_nome");
            Long codigoDaProvincia = Long.parseLong(request.getParameter("municipio_provincia"));
            
            Provincia provincia = new ProvinciaService().buscarPeloCodigo(codigoDaProvincia);
            
            Municipio municipio = new Municipio();
            municipio.setNome(nomeDoMunicipio);
            municipio.setProvincia(provincia);
            
            retorno = municipioService.salvar(municipio);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("municipio_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            String nomeDoMunicipio = request.getParameter("municipio_nome");
            Long codigoDoMunicipio = Long.parseLong(request.getParameter("municipio_provincia"));
            Long codigoDaProvincia = Long.parseLong(request.getParameter("municipio_provincia"));
            
            Provincia provincia = new ProvinciaService().buscarPeloCodigo(codigoDaProvincia);
            
            Municipio municipio = new Municipio();
            municipio.setCodigo(codigoDoMunicipio);
            municipio.setNome(nomeDoMunicipio);
            municipio.setProvincia(provincia);
            
            retorno = municipioService.editar(municipio);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("municipio_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("municipio_codigo"));
            
            retorno = municipioService.excluir(codigo);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("municipio_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("municipio_codigo"));
            
            Municipio municipio = municipioService.buscarPeloCodigo(codigo);

            sessao.setAttribute("municipio", municipio);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("municipio_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Municipio> municipios = municipioService.buscarTudo();

            sessao.setAttribute("municipios", municipios);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("municipio_mensagem", retorno);
        }

        response.sendRedirect("");
    }   
}