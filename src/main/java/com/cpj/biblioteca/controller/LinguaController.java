/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Lingua;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.LinguaService;
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
 * @author toshiba
 */
public class LinguaController extends HttpServlet {
    private String retorno;
    private LinguaService linguaService = new LinguaService();

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
            String nomeDaCategoria = request.getParameter("lingua_nome");

            Lingua lingua = new Lingua();
            lingua.setNome(nomeDaCategoria);

            retorno = linguaService.salvar(lingua);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("lingua_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("lingua_codigo"));
            String nomeDaCategoria = request.getParameter("lingua_nome");
            
            Lingua lingua = new Lingua(codigo, nomeDaCategoria);

            retorno = linguaService.editar(lingua);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("lingua_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("lingua_codigo"));
            
            retorno = linguaService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("lingua_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("lingua_codigo"));
            
            Lingua lingua = linguaService.buscarPeloCodigo(codigo);

            sessao.setAttribute("lingua", lingua);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("lingua_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Lingua> linguas = linguaService.buscarTudo();

            sessao.setAttribute("linguas", linguas);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("lingua_mensagem", retorno);
        }

        response.sendRedirect("");
    }
}