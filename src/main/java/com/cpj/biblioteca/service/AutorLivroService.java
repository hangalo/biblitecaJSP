/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.AutorLivroDAO;
import com.cpj.biblioteca.modelo.AutorLivro;
import com.cpj.biblioteca.modelo.AutorLivroCodigo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class AutorLivroService {
    private AutorLivroDAO emprestimoDAO = new AutorLivroDAO();
    
    public String salvar(AutorLivro autorLivro) throws CPJException{
        try {
            if(autorLivro.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(autorLivro.getCodigo().getAutor()== null) {
                return "Seleccione o autor";
            }
            return emprestimoDAO.salvar(autorLivro)? "Autor associado ao livro salvo com sucesso.":"Aconteceu um erro ao salvar o empréstimo. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(AutorLivro autorLivro) throws CPJException{
        try {
            if(autorLivro.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(autorLivro.getCodigo().getAutor()== null) {
                return "Seleccione o autor";
            }
            return emprestimoDAO.editar(autorLivro)? "Empréstimo editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(AutorLivro autorLivro) throws CPJException{
        return excluir(autorLivro.getCodigo());
    }
    
    public String excluir(AutorLivroCodigo autorLivro) throws CPJException{
        try {
            if(autorLivro == null) {
                return "Nenhum empréstimo informado. Seleccione o empréstimo a ser excluído.";
            }
            return emprestimoDAO.excluir(autorLivro)? "Empréstimo excluído com sucesso.":"Código inexistente. Não foi possível excluír o empréstimo informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public AutorLivro buscarPeloCodigo(Long codigoEmprestimo) throws CPJException{
        try {
            return emprestimoDAO.buscarPeloCodigo(codigoEmprestimo);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<AutorLivro> buscarTudo() throws CPJException{
        try {
            return emprestimoDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<AutorLivro> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return emprestimoDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
