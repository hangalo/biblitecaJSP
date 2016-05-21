/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.LivroDAO;
import com.cpj.biblioteca.modelo.Livro;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class LivroService {
    private LivroDAO livroDAO = new LivroDAO();
    
    public String salvar(Livro livro) throws CPJException{
        try {
            if(livro.getTitulo()== null || livro.getTitulo().isEmpty()) {
                return "O título do livro é obrigatório";
            }
            if(livro.getEditora()== null) {
                return "Seleccione a editora";
            }
            return livroDAO.salvar(livro)? "Livro salvo com sucesso.":"Aconteceu um erro ao salvar o livro. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Livro livro) throws CPJException{
        try {
            if(livro.getTitulo()== null || livro.getTitulo().isEmpty()) {
                return "O título do livro é obrigatório";
            }
            if(livro.getEditora() == null) {
                return "Informe a editora";
            }
            return livroDAO.editar(livro)? "Livro editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Livro livro) throws CPJException{
        return excluir(livro.getCodigo());
    }
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum livro informado. Seleccione o livro a ser excluído.";
            }
            return livroDAO.excluir(codigo)? "Livro excluído com sucesso.":"Código inexistente. Não foi possível excluír o livro informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Livro buscarPeloCodigo(Long codigoLivro) throws CPJException{
        try {
            return livroDAO.buscarPeloCodigo(codigoLivro);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Livro> buscarTudo() throws CPJException{
        try {
            return livroDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<Livro> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return livroDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
