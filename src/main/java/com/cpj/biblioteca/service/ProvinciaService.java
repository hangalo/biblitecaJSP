package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.DAO;
import com.cpj.biblioteca.dao.ProvinciaDAO;
import com.cpj.biblioteca.modelo.Provincia;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class ProvinciaService {
    private DAO<Provincia> provinciaDAO = new ProvinciaDAO();
    
    public String salvar(Provincia provincia) throws CPJException{
        try {
            if(provincia.getNome() == null || provincia.getNome().isEmpty()) {
                return "O nome da provincia é obrigatório";
            }
            return provinciaDAO.salvar(provincia)? "Provincia salva com sucesso.":"Aconteceu um erro ao salvar a provincia. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Provincia provincia) throws CPJException{
        try {
            if(provincia.getNome() == null || provincia.getNome().isEmpty()) {
                return "O nome da provincia é obrigatório";
            }
            return provinciaDAO.editar(provincia)? "Provincia editada com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Provincia provincia) throws CPJException{
        return excluir(provincia.getCodigo());
    }
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhuma provincia informada. Selecione a provincia a ser excluída.";
            }
            return provinciaDAO.excluir(codigo)? "Provincia excluida com sucesso.":"Código inexistente. Não foi possível excluír a provincia informada.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Provincia buscarPeloCodigo(Provincia codigoProvincia) throws CPJException{
        try {
            return provinciaDAO.buscarPeloCodigo(codigoProvincia);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Provincia> buscarTudo() throws CPJException{
        try {
            return provinciaDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
