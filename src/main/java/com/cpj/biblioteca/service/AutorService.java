/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.AutorDAO;
import com.cpj.biblioteca.modelo.Autor;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class AutorService {
    private AutorDAO autorDAO = new AutorDAO();
    
    public String salvar(Autor autor) throws CPJException{
        try {
            if(autor.getNome() == null || autor.getNome().isEmpty()) {
                return "O nome do autor é obrigatório";
            }
            if(autor.getSobrenome() == null || autor.getSobrenome().isEmpty()) {
                return "O sobrenome do autor é obrigatório";
            }
            if(autor.getMunicipio() == null) {
                return "Seleccione o município";
            }
            return autorDAO.salvar(autor)? "Autor salvo com sucesso.":"Aconteceu um erro ao salvar o autor. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Autor autor) throws CPJException{
        try {
            if(autor.getNome() == null || autor.getNome().isEmpty()) {
                return "O nome do autor é obrigatório";
            }
            if(autor.getSobrenome() == null || autor.getSobrenome().isEmpty()) {
                return "O sobrenome do autor é obrigatório";
            }
            if(autor.getMunicipio() == null) {
                return "Seleccione o município";
            }
            return autorDAO.editar(autor)? "Autor editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Autor autor) throws CPJException{
        return excluir(autor.getCodigo());
    }
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum autor informado. Seleccione o autor a ser excluído.";
            }
            return autorDAO.excluir(codigo)? "Autor excluído com sucesso.":"Código inexistente. Não foi possível excluír o livro informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Autor buscarPeloCodigo(Long codigoAutor) throws CPJException{
        try {
            return autorDAO.buscarPeloCodigo(codigoAutor);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Autor> buscarTudo() throws CPJException{
        try {
            return autorDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<Autor> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return autorDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
