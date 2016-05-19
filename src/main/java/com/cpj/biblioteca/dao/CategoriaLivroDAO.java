/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Categoria;
import com.cpj.biblioteca.modelo.CategoriaLivro;
import com.cpj.biblioteca.modelo.CategoriaLivroCodigo;
import com.cpj.biblioteca.modelo.Livro;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class CategoriaLivroDAO implements DAO<CategoriaLivro> {

    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    @Override
    public boolean salvar(CategoriaLivro categoriaLivro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO livro_categoria(livro_id_livro, categoria_id_categoria, data_registo) VALUES (?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, categoriaLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(2, categoriaLivro.getCodigo().getCategoria().getCodigo());
            prepareStatement.setDate(3, new Date(categoriaLivro.getDataRegisto().getTime()));
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(CategoriaLivro categoriaLivro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE livro_categoria SET data_registo = ? WHERE livro_id_livro = ? AND categoria_id_categoria = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setDate(1, new Date(categoriaLivro.getDataRegisto().getTime()));
            prepareStatement.setLong(2, categoriaLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(3, categoriaLivro.getCodigo().getCategoria().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }
    
    public boolean editarCategoriaDoLivro(CategoriaLivro categoriaLivro, Long codigoCategoriaAntigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE livro_categoria SET categoria_id_categoria = ? WHERE livro_id_livro = ? AND categoria_id_categoria = ? ";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, categoriaLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(2, categoriaLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(3, codigoCategoriaAntigo);
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(CategoriaLivro entidade) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM livro_categoria WHERE categoria_id_categoria = ? AND livro_id_livro = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, entidade.getCodigo().getCategoria().getCodigo());
            prepareStatement.setLong(2, entidade.getCodigo().getLivro().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public CategoriaLivro buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM livro_categoria, categoria, livro "
                    + "WHERE categoria_id_categoria = id_categoria AND livro_id_livro = id_livro AND id_categoria = ? AND id_livro = ? ";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, ((CategoriaLivroCodigo) codigo).getCategoria().getCodigo());
            prepareStatement.setLong(2, ((CategoriaLivroCodigo) codigo).getLivro().getCodigo());

            resultSet = prepareStatement.executeQuery();
            CategoriaLivro categoriaLivro = new CategoriaLivro();
            
            if (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setCodigo(resultSet.getLong("categoria.id_categoria"));
                categoria.setNome(resultSet.getString("categoria.nome_categoria"));

                Livro livro = new Livro();
                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));

                categoriaLivro.setCodigo(new CategoriaLivroCodigo(livro, categoria));
                categoriaLivro.setDataRegisto(resultSet.getDate("livro_categoria.data_registo"));
            }

            return categoriaLivro;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<CategoriaLivro> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    public List<CategoriaLivro> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<CategoriaLivro> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM livro_categoria, categoria, livro "
                    + " WHERE categoria_id_categoria = id_categoria AND livro_id_livro = id_livro ";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<CategoriaLivro> categoriasLivros = new ArrayList<>();
            while (resultSet.next()) {
                CategoriaLivro categoriaLivro = new CategoriaLivro();
                Categoria categoria = new Categoria();
                categoria.setCodigo(resultSet.getLong("categoria.id_categoria"));
                categoria.setNome(resultSet.getString("categoria.nome_categoria"));

                Livro livro = new Livro();
                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));

                categoriaLivro.setCodigo(new CategoriaLivroCodigo(livro, categoria));
                categoriaLivro.setDataRegisto(resultSet.getDate("livro_categoria.data_registo"));
                categoriasLivros.add(categoriaLivro);
            }
            return categoriasLivros;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
    
    public List<Livro> filtrarLivrosPeloCodigoDaCategoria(Long codigoCategoria, Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltroPorCategoria(codigoCategoria, linhaInicial, totalDeLinhas, true);
    }
    
    public List<Livro> filtrarLivrosPeloCodigoDaCategoria(Categoria categoria, Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltroPorCategoria(categoria.getCodigo(), linhaInicial, totalDeLinhas, true);
    }
    
    private List<Livro> aplicarFiltroPorCategoria(Long codigoCategoria, Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT livro.* FROM livro_categoria, livro "
                    + " WHERE categoria_id_categoria = id_categoria AND livro_id_livro = id_livro AND id_categoria = ? ";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, codigoCategoria);
            if (filtroActivo) {
                prepareStatement.setLong(2, linhaInicial);
                prepareStatement.setLong(3, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<Livro> livros = new ArrayList<>();
            while (resultSet.next()) {
                Livro livro = new Livro();
                livro.setCodigo(resultSet.getLong("livro.id_livro"));
                livro.setTitulo(resultSet.getString("livro.titulo"));
                livro.setIsbn(resultSet.getString("livro.isbn"));
                livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
                livro.setEdicao(resultSet.getString("livro.edicao"));
                livro.setResumo(resultSet.getString("livro.resumo"));
                livro.setSessao(resultSet.getString("livro.sessao"));
                livro.setEstante(resultSet.getInt("livro.estante"));
                livro.setPosicao(resultSet.getInt("livro.posicao"));
                
                livros.add(livro);
            }
            return livros;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
    
    public Livro filtrarCategoriasDoLivro(Livro livro, Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltroPorLivro(livro, linhaInicial, totalDeLinhas, true);
    }
    
    private Livro aplicarFiltroPorLivro(Livro livro, Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT categoria.* FROM livro_categoria, categoria "
                    + " WHERE categoria_id_categoria = id_categoria AND livro_id_livro = ? ";
            
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, livro.getCodigo());
            
            resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setCodigo(resultSet.getLong("categoria.id_categoria"));
                categoria.setNome(resultSet.getString("categoria.nome_categoria"));
                
                livro.addCategoria(categoria);
            }
            return livro;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
