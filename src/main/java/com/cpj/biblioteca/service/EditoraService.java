/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.EditoraDAO;
import com.cpj.biblioteca.modelo.Editora;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class EditoraService {
    private EditoraDAO editoraDAO = new EditoraDAO();
    
    public String salvar(Editora editora) throws CPJException{
        try {
            if(editora.getNome() == null || editora.getNome().isEmpty()) {
                return "O nome do município é obrigatório";
            }
            if(editora.getMunicipio() == null) {
                return "Seleccione o município";
            }
            return editoraDAO.salvar(editora)? "Município salvo com sucesso.":"Aconteceu um erro ao salvar o município. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Editora editora) throws CPJException{
        try {
            if(editora.getNome() == null || editora.getNome().isEmpty()) {
                return "O nome do município é obrigatório";
            }
            if(editora.getMunicipio()== null) {
                return "Seleccione a província";
            }
            return editoraDAO.editar(editora)? "Município editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Editora editora) throws CPJException{
        return excluir(editora.getCodigo());
    }
    
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum município informado. Seleccione o município a ser excluído.";
            }
            return editoraDAO.excluir(codigo)? "Município excluído com sucesso.":"Código inexistente. Não foi possível excluír o município informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Editora buscarPeloCodigo(Long codigoMunicipio) throws CPJException{
        try {
            return editoraDAO.buscarPeloCodigo(codigoMunicipio);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Editora> buscarTudo() throws CPJException{
        try {
            return editoraDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Editora> filtar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return editoraDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}