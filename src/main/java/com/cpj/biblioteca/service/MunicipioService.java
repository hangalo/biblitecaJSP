package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.DAO;
import com.cpj.biblioteca.dao.MunicipioDAO;
import com.cpj.biblioteca.modelo.Municipio;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class MunicipioService {
    private DAO<Municipio> municipioDAO = new MunicipioDAO();
    
    public String salvar(Municipio municipio) throws CPJException{
        try {
            if(municipio.getNome() == null || municipio.getNome().isEmpty()) {
                return "O nome do município é obrigatório";
            }
            if(municipio.getProvincia() == null) {
                return "Seleccione a província";
            }
            return municipioDAO.salvar(municipio)? "Município salvo com sucesso.":"Aconteceu um erro ao salvar o município. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Municipio municipio) throws CPJException{
        try {
            if(municipio.getNome() == null || municipio.getNome().isEmpty()) {
                return "O nome do município é obrigatório";
            }
            if(municipio.getProvincia() == null) {
                return "Seleccione a província";
            }
            return municipioDAO.editar(municipio)? "Município editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Municipio municipio) throws CPJException{
        return excluir(municipio.getCodigo());
    }
    
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum município informado. Seleccione o município a ser excluído.";
            }
            return municipioDAO.excluir(codigo)? "Município excluído com sucesso.":"Código inexistente. Não foi possível excluír o município informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Municipio buscarPeloCodigo(Municipio codigoMunicipio) throws CPJException{
        try {
            return municipioDAO.buscarPeloCodigo(codigoMunicipio);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Municipio> buscarTudo() throws CPJException{
        try {
            return municipioDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
