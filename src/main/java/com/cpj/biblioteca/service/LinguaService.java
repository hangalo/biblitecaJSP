package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.DAO;
import com.cpj.biblioteca.dao.LinguaDAO;
import com.cpj.biblioteca.modelo.Lingua;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class LinguaService {
    private DAO<Lingua> linguaDAO = new LinguaDAO();
    
    public String salvar(Lingua lingua) throws CPJException{
        try {
            if(lingua.getNome() == null || lingua.getNome().isEmpty()) {
                return "O nome da língua é obrigatório";
            }
            return linguaDAO.salvar(lingua)? "Língua salva com sucesso.":"Aconteceu um erro ao salvar a língua. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Lingua lingua) throws CPJException{
        try {
            if(lingua.getNome() == null || lingua.getNome().isEmpty()) {
                return "O nome da língua é obrigatório";
            }
            return linguaDAO.editar(lingua)? "Língua editada com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Lingua lingua) throws CPJException{
        return excluir(lingua.getCodigo());
    }
   
    public String excluir(Long codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhuma língua informada. Selecione a língua a ser excluída.";
            }
            return linguaDAO.excluir(codigo)? "Língua excluida com sucesso.":"Código inexistente. Não foi possível excluír a língua informada.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Lingua buscarPeloCodigo(Long codigoLingua) throws CPJException{
        try {
            return linguaDAO.buscarPeloCodigo(codigoLingua);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Lingua> buscarTudo() throws CPJException{
        try {
            return linguaDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
