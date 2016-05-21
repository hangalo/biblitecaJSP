/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.AutorLivro;
import com.cpj.biblioteca.modelo.AutorLivroCodigo;
import com.cpj.biblioteca.modelo.Livro;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toshiba
 */
public class AutorLivroDAO implements DAO<AutorLivro>{
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    @Override
    public boolean salvar(AutorLivro autorLivro)  throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO autor_livro(livro_id_livro, autor_id_autor, ano) VALUES (?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, autorLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(2, autorLivro.getCodigo().getAutor().getCodigo());
            prepareStatement.setInt(3, autorLivro.getAno());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(AutorLivro autorLivro) throws ClassNotFoundException, SQLException {
        try {//livro_id_livro, autor_id_autor, ano
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE autor_livro SET ano = ? WHERE livro_id_livro = ? AND autor_id_autor = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setInt(1, autorLivro.getAno());
            prepareStatement.setLong(2, autorLivro.getCodigo().getLivro().getCodigo());
            prepareStatement.setLong(3, autorLivro.getCodigo().getAutor().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(AutorLivro autorLivro) throws ClassNotFoundException, SQLException {
        return excluir(autorLivro.getCodigo());
    }

    @Override
    public boolean excluir(Serializable autorLivro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM autor_livro WHERE livro_id_livro = ? AND autor_id_autor = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(2, ((AutorLivroCodigo)autorLivro).getLivro().getCodigo());
            prepareStatement.setLong(3, ((AutorLivroCodigo)autorLivro).getAutor().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public AutorLivro buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM autor_livro, autor, livro "
                       + "WHERE livro_id_livro = id_livro AND id_livro = ? AND autor_id_autor = id_autor AND id_autor = ?";
            
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, ((AutorLivroCodigo)codigo).getAutor().getCodigo());
            prepareStatement.setLong(2, ((AutorLivroCodigo)codigo).getLivro().getCodigo());
            
            resultSet = prepareStatement.executeQuery();

            AutorLivro autorLivro = new AutorLivro();
            if (resultSet.next()) {
                
                Autor autor = new Autor();
                autor.setCodigo(resultSet.getLong("livro.id_autor"));
                autor.setNome(resultSet.getString("livro.nome_autor"));
                autor.setSobrenome(resultSet.getString("livro.sobrenome_autor"));
                autor.setDataNascimento(resultSet.getDate("livro.data_nascimento"));
                autor.setBiografia(resultSet.getString("livro.breve_biografica"));
                
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

                autorLivro.setCodigo(new AutorLivroCodigo(autor, livro));
                autorLivro.setAno(resultSet.getInt("autor_livro.ano"));
            }
            return autorLivro;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<AutorLivro> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }
    
    public List<AutorLivro> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }
    
    private List<AutorLivro> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM autor_livro, autor, livro "
                       + "WHERE livro_id_livro = id_livro AND autor_id_autor = id_autor ";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<AutorLivro> autorLivros = new ArrayList<>();
            while (resultSet.next()) {
                AutorLivro autorLivro = new AutorLivro();
                
                Autor autor = new Autor();
                autor.setCodigo(resultSet.getLong("livro.id_autor"));
                autor.setNome(resultSet.getString("livro.nome_autor"));
                autor.setSobrenome(resultSet.getString("livro.sobrenome_autor"));
                autor.setDataNascimento(resultSet.getDate("livro.data_nascimento"));
                autor.setBiografia(resultSet.getString("livro.breve_biografica"));
                
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

                autorLivro.setCodigo(new AutorLivroCodigo(autor, livro));
                autorLivro.setAno(resultSet.getInt("autor_livro.ano"));
                autorLivros.add(autorLivro);
            }
            return autorLivros;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
    
    public List<Livro> filtrarLivrosDoAutor(Long codigoAutor, Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltroDosLivrosDoAutor(codigoAutor, linhaInicial, totalDeLinhas, true);
    }
    
    private List<Livro> aplicarFiltroDosLivrosDoAutor(Long codigoAutor, Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM autor_livro, livro "
                       + "WHERE livro_id_livro = id_livro AND autor_id_autor = id_autor AND id_autor = ? ";
            
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, codigoAutor);
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
}
