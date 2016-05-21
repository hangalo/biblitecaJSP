/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.LeitorDAO;
import com.cpj.biblioteca.modelo.Leitor;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class LeitorService {
    private LeitorDAO leitorDAO = new LeitorDAO();
    
    public String salvar(Leitor leitor) throws CPJException{
        try {
            if(leitor.getNome() == null || leitor.getNome().isEmpty()) {
                return "O nome do leitor é obrigatório";
            }
            if(leitor.getSobrenome() == null || leitor.getSobrenome().isEmpty()) {
                return "O sobrenome do leitor é obrigatório";
            }
            if(leitor.getMunicipio() == null) {
                return "Seleccione o município";
            }
            return leitorDAO.salvar(leitor)? "Livro salvo com sucesso.":"Aconteceu um erro ao salvar o livro. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Leitor leitor) throws CPJException{
        try {
            if(leitor.getNome() == null || leitor.getNome().isEmpty()) {
                return "O nome do leitor é obrigatório";
            }
            if(leitor.getSobrenome() == null || leitor.getSobrenome().isEmpty()) {
                return "O sobrenome do leitor é obrigatório";
            }
            if(leitor.getMunicipio() == null) {
                return "Seleccione o município";
            }
            return leitorDAO.editar(leitor)? "Livro editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Leitor leitor) throws CPJException{
        return excluir(leitor.getCodigo());
    }
    
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum livro informado. Seleccione o livro a ser excluído.";
            }
            return leitorDAO.excluir(codigo)? "Livro excluído com sucesso.":"Código inexistente. Não foi possível excluír o livro informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Leitor buscarPeloCodigo(Long codigoLeitor) throws CPJException{
        try {
            return leitorDAO.buscarPeloCodigo(codigoLeitor);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Leitor> buscarTudo() throws CPJException{
        try {
            return leitorDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<Leitor> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return leitorDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
