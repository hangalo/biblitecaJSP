package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Categoria;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.CategoriaService;
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
public class CategoriaController extends HttpServlet {

    private String retorno;
    private CategoriaService categoriaService = new CategoriaService();

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
            String nomeDaCategoria = request.getParameter("categoria_nome");

            Categoria categoria = new Categoria();
            categoria.setNome(nomeDaCategoria);

            retorno = categoriaService.salvar(categoria);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("categoria_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("categoria_codigo"));
            String nomeDaCategoria = request.getParameter("categoria_nome");
            
            Categoria categoria = new Categoria(codigo, nomeDaCategoria);

            retorno = categoriaService.editar(categoria);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("categoria_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("categoria_codigo"));
            
            retorno = categoriaService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("categoria_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("categoria_codigo"));
            
            Categoria categoria = categoriaService.buscarPeloCodigo(codigo);

            sessao.setAttribute("categoria", categoria);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("categoria_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Categoria> categorias = categoriaService.buscarTudo();

            sessao.setAttribute("categorias", categorias);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("categoria_mensagem", retorno);
        }

        response.sendRedirect("");
    }   
}