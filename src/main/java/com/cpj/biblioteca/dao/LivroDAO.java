package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Editora;
import com.cpj.biblioteca.modelo.Lingua;
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
 * @author Chandimba
 */
public class LivroDAO implements DAO<Livro> {

    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    //id_livro, isbn, titulo, data_publicacao, id_lingua, edicao, resumo, sessao, estante, posicao, id_editora
    @Override
    public boolean salvar(Livro livro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO livro(isbn, titulo, data_publicacao, id_lingua, edicao, resumo, sessao, estante, posicao, id_editora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, livro.getIsbn());
            prepareStatement.setString(2, livro.getTitulo());
            prepareStatement.setDate(3, new Date(livro.getDataPublicacao().getTime()));
            prepareStatement.setLong(4, livro.getLingua().getCodigo());
            prepareStatement.setString(5, livro.getEdicao());
            prepareStatement.setString(6, livro.getResumo());
            prepareStatement.setString(7, livro.getSessao());
            prepareStatement.setLong(8, livro.getEstante());
            prepareStatement.setLong(9, livro.getPosicao());
            prepareStatement.setLong(10, livro.getEditora().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Livro livro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE livro SER isbn = ?, titulo = ?, data_publicacao = ?, id_lingua = ?, edicao = ?, resumo = ?, sessao = ?, estante = ?, posicao = ?, id_editora = ? "
                    + "WHERE id_livro = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, livro.getIsbn());
            prepareStatement.setString(2, livro.getTitulo());
            prepareStatement.setDate(3, new Date(livro.getDataPublicacao().getTime()));
            prepareStatement.setLong(4, livro.getLingua().getCodigo());
            prepareStatement.setString(5, livro.getEdicao());
            prepareStatement.setString(6, livro.getResumo());
            prepareStatement.setString(7, livro.getSessao());
            prepareStatement.setLong(8, livro.getEstante());
            prepareStatement.setLong(9, livro.getPosicao());
            prepareStatement.setLong(10, livro.getEditora().getCodigo());
            prepareStatement.setLong(11, livro.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Livro livro) throws ClassNotFoundException, SQLException {
        return excluir(livro.getCodigo());
    }

    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM livro WHERE id_livro = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long) codigo);
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Livro buscarPeloCodigo(Livro livro) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua "
                    + "WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora AND id_livro = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, livro.getCodigo());
            resultSet = prepareStatement.executeQuery();

            return resultSet.next()? obterDadosDoLivro(resultSet): null;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    public Livro buscarPeloISBN(String isbn) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua "
                    + "WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora AND isbn = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, isbn);
            resultSet = prepareStatement.executeQuery();
            
            return resultSet.next()? obterDadosDoLivro(resultSet): null;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    public Livro obterDadosDoLivro(ResultSet resultSet) throws SQLException {
        Livro livro = new Livro();

        Lingua lingua = new Lingua();
        Editora editora = new Editora();

        livro.setCodigo(resultSet.getLong("livro.id_livro"));
        livro.setTitulo(resultSet.getString("livro.titulo"));
        livro.setIsbn(resultSet.getString("livro.isbn"));
        livro.setDataPublicacao(resultSet.getDate("livro.data_publicacao"));
        livro.setEdicao(resultSet.getString("livro.edicao"));
        livro.setResumo(resultSet.getString("livro.resumo"));
        livro.setSessao(resultSet.getString("livro.sessao"));
        livro.setEstante(resultSet.getInt("livro.estante"));
        livro.setPosicao(resultSet.getInt("livro.posicao"));

        lingua.setCodigo(resultSet.getLong("lingua.id_lingua"));
        lingua.setNome(resultSet.getString("lingua.nome_lingua"));

        editora.setCodigo(resultSet.getLong("editora.id_editora"));
        editora.setNome(resultSet.getString("editora.nome_editora"));
        editora.setRua(resultSet.getString("editora.rua_editora"));
        editora.setCasa(resultSet.getString("editora.casa_editora"));

        livro.setLingua(lingua);
        livro.setEditora(editora);
        
        return livro;
    }

    @Override
    public List<Livro> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    public List<Livro> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<Livro> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT livro.*, lingua.*, editora.*  FROM livro, editora, lingua "
                    + "WHERE livro.id_lingua = lingua.id_lingua AND livro.id_editora = editora.id_editora";
            if (filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }

            prepareStatement = conexao.prepareStatement(sql);
            if (filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }

            resultSet = prepareStatement.executeQuery();

            List<Livro> livros = new ArrayList<>();
            while (resultSet.next()) {
                livros.add(obterDadosDoLivro(resultSet));
            }
            return livros;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
    
}
