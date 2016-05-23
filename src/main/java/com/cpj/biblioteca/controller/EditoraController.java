package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Municipio;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.EditoraService;
import com.cpj.biblioteca.service.MunicipioService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chandimba
 */
public class EditoraController extends HttpServlet {

    private String retorno;
    private EditoraService editoraService = new EditoraService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        buscarPeloCodigo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        salvar(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        editar(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        excluir(req, resp);
    }

    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            String nomeDaEditora = request.getParameter("editora_nome");
            String casaDaEditora = request.getParameter("editora_casa");
            String ruaDaEditora = request.getParameter("editora_rua");
            Long codigoMunicipio = Long.parseLong(request.getParameter("editora_municipio"));
            
            Municipio municipio = new MunicipioService().buscarPeloCodigo(codigoMunicipio);
            
            
            Editora editora = new Editora();
            editora.setNome(nomeDaEditora);
            editora.setCasa(casaDaEditora);
            editora.setRua(ruaDaEditora);
            editora.setMunicipio(municipio);

            retorno = editoraService.salvar(editora);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("editora_mensagem", retorno);
        response.sendRedirect("");
    }

    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigoEditora = Long.parseLong(request.getParameter("editora_codigo"));
            String nomeDaEditora = request.getParameter("editora_nome");
            String casaDaEditora = request.getParameter("editora_casa");
            String ruaDaEditora = request.getParameter("editora_rua");
            Long codigoMunicipio = Long.parseLong(request.getParameter("editora_municipio"));
            
            Municipio municipio = new MunicipioService().buscarPeloCodigo(codigoMunicipio);
            
            Editora editora = new Editora();
            editora.setCodigo(codigoEditora);
            editora.setNome(nomeDaEditora);
            editora.setCasa(casaDaEditora);
            editora.setRua(ruaDaEditora);
            editora.setMunicipio(municipio);

            retorno = editoraService.salvar(editora);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }
        
        sessao.setAttribute("editora_mensagem", retorno);
        response.sendRedirect("");
    }

    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("editora_codigo"));

            retorno = editoraService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("editora_mensagem", retorno);
        response.sendRedirect("");
    }

    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("editora_codigo"));

            Editora lingua = editoraService.buscarPeloCodigo(codigo);

            sessao.setAttribute("editora", lingua);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("editora_mensagem", retorno);
        }

        response.sendRedirect("");
    }

    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Editora> editoras = editoraService.buscarTudo();

            sessao.setAttribute("editoras", editoras);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("editora_mensagem", retorno);
        }

        response.sendRedirect("");
    }
}
