package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.EditoraDAO;
import com.cpj.biblioteca.modelo.Editora;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class EditoraService {
    private EditoraDAO editoraDAO = new EditoraDAO();
    
    public String salvar(Editora editora) throws CPJException{
        if(editora.getNome() == null || editora.getNome().isEmpty()) {
            return "O nome do município é obrigatório";
        }
        if(editora.getMunicipio() == null) {
            return "Seleccione o município";
        }
        return editoraDAO.salvar(editora)? "Município salvo com sucesso.":"Aconteceu um erro ao salvar o município. Consulte o administrador do sistema para mais informações.";
    }
    
    public String editar(Editora editora) throws CPJException{
        if(editora.getNome() == null || editora.getNome().isEmpty()) {
            return "O nome do município é obrigatório";
        }
        if(editora.getMunicipio()== null) {
            return "Seleccione a província";
        }
        return editoraDAO.editar(editora)? "Município editado com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
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
    
    public Editora buscarPeloCodigo(Editora codigoMunicipio) throws CPJException{
        return editoraDAO.buscarPeloCodigo(codigoMunicipio);
    }
    
    public List<Editora> buscarTudo() throws CPJException{
        return editoraDAO.buscarTudo();
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