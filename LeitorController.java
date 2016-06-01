/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Leitor;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.LeitorService;
import com.cpj.biblioteca.service.MunicipioService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author Adelino Eduardo
 */
public class LeitorController extends HttpServlet{
    
    private String retorno;
    private LeitorService leitorService = new LeitorService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            salvar(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LeitorController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    protected void doput(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            editar(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LeitorController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession sessao = request.getSession();
        try
        {
            Leitor leitor = new Leitor();
            
            leitor.setCodigo(Long.parseLong(request.getParameter("leitor_codigo")));
            leitor.setNome(request.getParameter("leitor_nome"));
            leitor.setSobrenome(request.getParameter("leitor_sobrenome"));
            leitor.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("leitor_datanascimento")));
            leitor.setMunicipio(new MunicipioService().buscarPeloCodigo(Long.parseLong(request.getParameter("leitor_municipio"))));
            leitor.setBairro(request.getParameter("leitor_bairro"));
            leitor.setRua(request.getParameter("leitor_rua"));
            leitor.setCasa(request.getParameter("leitor_casa"));
            leitor.setDataInscricao(Calendar.getInstance().getTime());  
            
            retorno = leitorService.salvar(leitor);
        }
        catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de nascimento do autor";
        }
        sessao.setAttribute("leitor_message", retorno);
        response.sendRedirect(""); 
    }
    
    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession sessao = request.getSession();
        try
        {
            Leitor leitor = new Leitor();
            
            leitor.setCodigo(Long.parseLong(request.getParameter("leitor_codigo")));
            leitor.setNome(request.getParameter("leitor_nome"));
            leitor.setSobrenome(request.getParameter("leitor_sobrenome"));
            leitor.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("leitor_datanascimento")));
            leitor.setMunicipio(new MunicipioService().buscarPeloCodigo(Long.parseLong(request.getParameter("leitor_municipio"))));
            leitor.setBairro(request.getParameter("leitor_bairro"));
            leitor.setRua(request.getParameter("leitor_rua"));
            leitor.setCasa(request.getParameter("leitor_casa"));
            leitor.setDataInscricao(Calendar.getInstance().getTime());  
            
            retorno = leitorService.editar(leitor);
        }
        catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de nascimento do Leitor";
        }
        sessao.setAttribute("leitor_message", retorno);
        response.sendRedirect(""); 
    }
    
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("leitor_codigo"));
            
            retorno = leitorService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("leitor_mensagem", retorno);
        response.sendRedirect("");
    }
    
    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("leitor_codigo"));
            
            Leitor leitor = leitorService.buscarPeloCodigo(codigo);

            sessao.setAttribute("leitor", leitor);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("leitor_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Leitor> leitores = leitorService.buscarTudo();

            sessao.setAttribute("autores", leitores);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("autor_mensagem", retorno);
        }

        response.sendRedirect("");
    }
    
}
