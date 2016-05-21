/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.EmprestimoDAO;
import com.cpj.biblioteca.modelo.Emprestimo;
import com.cpj.biblioteca.modelo.EmprestimoCodigo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    public String salvar(Emprestimo emprestimo) throws CPJException{
        try {
            if(emprestimo.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(emprestimo.getCodigo().getLeitor() == null) {
                return "Seleccione o leitor";
            }
            return emprestimoDAO.salvar(emprestimo)? "Empréstimo salvo com sucesso.":"Aconteceu um erro ao salvar o empréstimo. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Emprestimo emprestimo) throws CPJException{
        try {
            if(emprestimo.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(emprestimo.getCodigo().getLeitor() == null) {
                return "Seleccione o leitor";
            }
            return emprestimoDAO.editar(emprestimo)? "Empréstimo editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Emprestimo emprestimo) throws CPJException{
        return excluir(emprestimo.getCodigo());
    }
    
    
    public String excluir(EmprestimoCodigo codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum empréstimo informado. Seleccione o empréstimo a ser excluído.";
            }
            return emprestimoDAO.excluir(codigo)? "Empréstimo excluído com sucesso.":"Código inexistente. Não foi possível excluír o empréstimo informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Emprestimo buscarPeloCodigo(Long codigoEmprestimo) throws CPJException{
        try {
            return emprestimoDAO.buscarPeloCodigo(codigoEmprestimo);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Emprestimo> buscarTudo() throws CPJException{
        try {
            return emprestimoDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<Emprestimo> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return emprestimoDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
