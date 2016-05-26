package com.cpj.biblioteca.service;

import com.cpj.biblioteca.dao.CategoriaLivroDAO;
import com.cpj.biblioteca.modelo.Categoria;
import com.cpj.biblioteca.modelo.CategoriaLivro;
import com.cpj.biblioteca.modelo.CategoriaLivroCodigo;
import com.cpj.biblioteca.modelo.Livro;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class CategoriaLivroService {
    private CategoriaLivroDAO categoriaLivroDAO = new CategoriaLivroDAO();
    
    public String salvar(CategoriaLivro emprestimo) throws CPJException{
        try {
            if(emprestimo.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(emprestimo.getCodigo().getCategoria()== null) {
                return "Seleccione a categoria";
            }
            return categoriaLivroDAO.salvar(emprestimo)? "Categoria associado ao livro salvo com sucesso.":"Aconteceu um erro ao associar a categoria ao livro. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String editar(CategoriaLivro livro) throws CPJException{
        try {
            if(livro.getCodigo().getLivro() == null) {
                return "Seleccione o livro";
            }
            if(livro.getCodigo().getCategoria()== null) {
                return "Seleccione a categoria";
            }
            return categoriaLivroDAO.editar(livro)? "Edição realizada com sucesso.":"Não foi possível salvar a edição feita. Consulte o administrador do sistema para mais informações.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public String excluir(CategoriaLivro livro) throws CPJException{
        return excluir(livro.getCodigo());
    }
    
    
    public String excluir(CategoriaLivroCodigo codigo) throws CPJException{
        try {
            if(codigo == null) {
                return "Nenhum empréstimo informado. Seleccione o empréstimo a ser excluído.";
            }
            return categoriaLivroDAO.excluir(codigo)? "Categoria desassociada ao livro com sucesso.":"Código inexistente. Não foi possível desassociar a categoria do livro informado.";
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public CategoriaLivro buscarPeloCodigo(CategoriaLivro codigoEmprestimo) throws CPJException{
        try {
            return categoriaLivroDAO.buscarPeloCodigo(codigoEmprestimo);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<CategoriaLivro> buscarTudo() throws CPJException{
        try {
            return categoriaLivroDAO.buscarTudo();
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    
    public List<CategoriaLivro> filtrar(Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return categoriaLivroDAO.filtrar(linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Livro> filtrarLivrosPeloCodigoDaCategoria(Long codigoCategoria, Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return categoriaLivroDAO.filtrarLivrosPeloCodigoDaCategoria(codigoCategoria, linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
    
    public List<Livro> filtrarLivrosPeloCodigoDaCategoria(Categoria categoria, Long linhaInicial, Long totalDeLinhas) throws CPJException{
        try {
            return categoriaLivroDAO.filtrarLivrosPeloCodigoDaCategoria(categoria, linhaInicial, totalDeLinhas);
        } catch (ClassNotFoundException ex) {
            throw new CPJException("Driver do MySQL não encontrado.");
        } catch (SQLException ex) {
            throw new CPJException("Erro: " + ex.getMessage());
        }
    }
}
