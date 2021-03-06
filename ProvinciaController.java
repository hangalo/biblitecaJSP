package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Provincia;
import com.cpj.biblioteca.service.CPJException;
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
public class ProvinciaController  extends HttpServlet {
    
    private String retorno;
    private ProvinciaService provinciaService = new ProvinciaService();

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
            String nomeProvincia = request.getParameter("provincia_nome");

            Provincia provincia = new Provincia(nomeProvincia);

            retorno = provinciaService.salvar(provincia);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("provincia_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("provincia_codigo"));
            String nomeProvincia = request.getParameter("provincia_nome");
            
            Provincia provincia = new Provincia(codigo, nomeProvincia);

            retorno = provinciaService.editar(provincia);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("provincia_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("provincia_codigo"));
            
            retorno = provinciaService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("lingua_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("provincia_codigo"));
            
            Provincia provincia = provinciaService.buscarPeloCodigo(codigo);

            sessao.setAttribute("provincia", provincia);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("provincia_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Provincia> linguas = provinciaService.buscarTudo();

            sessao.setAttribute("provincias", linguas);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("provincia_mensagem", retorno);
        }

        response.sendRedirect("");
    }
}
