package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.CategoriaDAO;
import com.cpj.biblioteca.dao.DAO;
import com.cpj.biblioteca.modelo.Categoria;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class CategoriaService {
    private DAO<Categoria> categoriaDAO = new CategoriaDAO();
    
    public String salvar(Categoria categoria) throws CPJException{
        try {
            if(categoria.getNome() == null || categoria.getNome().isEmpty()) {
                return "O nome da categoria é obrigatório";
            }
            return categoriaDAO.salvar(categoria)? "Categoria salva com sucesso.":"Aconteceu um erro ao salvar a categoria. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(Categoria categoria) throws CPJException{
        try {
            if(categoria.getNome() == null || categoria.getNome().isEmpty()) {
                return "O nome da categoria é obrigatório";
            }
            return categoriaDAO.editar(categoria)? "Categoria editada com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(Categoria categoria) throws CPJException{
        return excluir(categoria.getCodigo());
    }
    
    public String excluir(Long categoria) throws CPJException{
        try {
            if(categoria == null) {
                return "Nenhuma categoria informada. Selecione a categoria a ser excluída.";
            }
            return categoriaDAO.excluir(categoria)? "Categoria excluida com sucesso.":"Código inexistente. Não foi possível excluír a categoria informada.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public Categoria buscarPeloCodigo(Categoria codigoCategoria) throws CPJException{
        try {
            return categoriaDAO.buscarPeloCodigo(codigoCategoria);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Categoria> buscarTudo() throws CPJException{
        try {
            return categoriaDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
