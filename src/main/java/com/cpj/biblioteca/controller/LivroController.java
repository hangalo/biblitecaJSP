package com.cpj.biblioteca.controller;

import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Lingua;
import com.cpj.biblioteca.modelo.Livro;
import com.cpj.biblioteca.service.CPJException;
import com.cpj.biblioteca.service.EditoraService;
import com.cpj.biblioteca.service.LinguaService;
import com.cpj.biblioteca.service.LivroService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class LivroController extends HttpServlet {
    
    private String retorno;
    private LivroService livroService = new LivroService();

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
            String tituloDoLivro = request.getParameter("livro_titulo");
            String isbnDoLivro = request.getParameter("livro_isbn");
            String edicaoDoLivro = request.getParameter("livro_edicao");
            String sessaoDoLivro = request.getParameter("livro_sessao");
            String resumoDoLivro = request.getParameter("livro_resumo");
            Integer estanteDoLivro = Integer.parseInt(request.getParameter("livro_estante"));
            Integer posicaoDoLivro = Integer.parseInt(request.getParameter("livro_posicao"));
            Date dataPublicacao = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("livro_datapublicacao"));
            Long codigoEditora = Long.parseLong(request.getParameter("livro_editora"));
            Long codigoLivro = Long.parseLong(request.getParameter("livro_lingua"));
            
            Editora editora = new EditoraService().buscarPeloCodigo(codigoEditora);
            Lingua lingua = new LinguaService().buscarPeloCodigo(codigoLivro);
            
            Livro livro = new Livro();
            livro.setEdicao(edicaoDoLivro);
            livro.setEditora(editora);
            livro.setIsbn(isbnDoLivro);
            livro.setDataPublicacao(dataPublicacao);
            livro.setAutores(null);
            livro.setCategorias(null);
            livro.setEstante(estanteDoLivro);
            livro.setPosicao(posicaoDoLivro);
            livro.setResumo(resumoDoLivro);
            livro.setSessao(sessaoDoLivro);
            livro.setTitulo(tituloDoLivro);
            livro.setLingua(lingua);
            
            retorno = livroService.salvar(livro);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de publicação do livro";
        }

        sessao.setAttribute("livro_mensagem", retorno);
        response.sendRedirect("");
    }

    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigoDoLivro = Long.parseLong(request.getParameter("livro_codigo"));
            String tituloDoLivro = request.getParameter("livro_titulo");
            String isbnDoLivro = request.getParameter("livro_isbn");
            String edicaoDoLivro = request.getParameter("livro_edicao");
            String sessaoDoLivro = request.getParameter("livro_sessao");
            String resumoDoLivro = request.getParameter("livro_resumo");
            Integer estanteDoLivro = Integer.parseInt(request.getParameter("livro_estante"));
            Integer posicaoDoLivro = Integer.parseInt(request.getParameter("livro_posicao"));
            Date dataPublicacao = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("livro_datapublicacao"));
            Long codigoEditora = Long.parseLong(request.getParameter("livro_editora"));
            Long codigoLivro = Long.parseLong(request.getParameter("livro_lingua"));
            
            Editora editora = new EditoraService().buscarPeloCodigo(codigoEditora);
            Lingua lingua = new LinguaService().buscarPeloCodigo(codigoLivro);
            
            Livro livro = new Livro();
            livro.setCodigo(codigoDoLivro);
            livro.setEdicao(edicaoDoLivro);
            livro.setEditora(editora);
            livro.setIsbn(isbnDoLivro);
            livro.setDataPublicacao(dataPublicacao);
            livro.setAutores(null);
            livro.setCategorias(null);
            livro.setEstante(estanteDoLivro);
            livro.setPosicao(posicaoDoLivro);
            livro.setResumo(resumoDoLivro);
            livro.setSessao(sessaoDoLivro);
            livro.setTitulo(tituloDoLivro);
            livro.setLingua(lingua);
            
            retorno = livroService.editar(livro);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        } catch (ParseException ex) {
            retorno = "Ocorreu um erro ao converter a data de publicação do livro";
        }

        sessao.setAttribute("livro_mensagem", retorno);
        response.sendRedirect("");
    }

    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("livro_codigo"));

            retorno = livroService.excluir(codigo);

        } catch (CPJException ex) {
            retorno = ex.getMessage();
        }

        sessao.setAttribute("livro_mensagem", retorno);
        response.sendRedirect("");
    }

    public void buscarPeloCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            Long codigo = Long.parseLong(request.getParameter("livro_codigo"));

            Livro livro = livroService.buscarPeloCodigo(codigo);

            sessao.setAttribute("livro", livro);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("livro_mensagem", retorno);
        }

        response.sendRedirect("");
    }

    public void buscarTudo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        try {
            List<Livro> livros = livroService.buscarTudo();

            sessao.setAttribute("livros", livros);
        } catch (CPJException ex) {
            retorno = ex.getMessage();
            sessao.setAttribute("livro_mensagem", retorno);
        }

        response.sendRedirect("");
    }
}
