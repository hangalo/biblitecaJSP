package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Autor;
import com.cpj.biblioteca.modelo.Municipio;
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
public class AutorDAO implements DAO<Autor> {

    private static final String BUSCAR_TUDO = "SELECT a.id_autor, a.nome_autor, a.sobrenome_autor, a.data_nascimento, a.breve_biografica, m.id_municipio from autor a inner join municipio m on a.id_municipio = m.id_municipio";
  
    Connection conexao;

    public AutorDAO() {
        conexao = Conexao.criarConexao();
    }

    @Override
    public boolean salvar(Autor autor) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO autor(nome_autor, sobrenome_autor, data_nascimento, breve_biografica, id_municipio) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, autor.getNome());
        prepareStatement.setString(2, autor.getSobrenome());
        prepareStatement.setDate(3, new Date(autor.getDataNascimento().getTime()));
        prepareStatement.setString(4, autor.getBiografia());
        prepareStatement.setLong(5, autor.getMunicipio().getCodigo());
        return prepareStatement.executeUpdate() > 0;
    }

    @Override
    public boolean editar(Autor autor) throws ClassNotFoundException, SQLException {

        String sql = "UPDATE autor SET nome_autor = ?, sobrenome_autor = ?, data_nascimento = ?, breve_biografica = ?, id_municipio = ? "
                + "WHERE id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, autor.getNome());
        prepareStatement.setString(2, autor.getSobrenome());
        prepareStatement.setDate(3, new Date(autor.getDataNascimento().getTime()));
        prepareStatement.setString(4, autor.getBiografia());
        prepareStatement.setLong(5, autor.getMunicipio().getCodigo());
        prepareStatement.setLong(6, autor.getCodigo());
        prepareStatement.close();
        return prepareStatement.executeUpdate() > 0;

    }

    @Override
    public boolean excluir(Autor autor) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM autor WHERE id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, autor.getCodigo());
        return prepareStatement.executeUpdate() > 0;

    }

    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM autor WHERE id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long) codigo);
        return prepareStatement.executeUpdate() > 0;

    }

    @Override
    public Autor buscarPeloCodigo(Autor autor) throws ClassNotFoundException, SQLException {
        ResultSet resultSet;
        Connection conexao = Conexao.criarConexao();
        String sql = "SELECT a.*, m.* FROM autor a, municipio m WHERE a.id_municipio = m.id_municipio AND id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, autor.getCodigo());
        resultSet = prepareStatement.executeQuery();

        Municipio municipio = new Municipio();
        if (resultSet.next()) {
            autor.setCodigo(resultSet.getLong("id_autor"));
            autor.setNome(resultSet.getString("nome_autor"));
            autor.setSobrenome(resultSet.getString("sobrenome_autor"));
            autor.setDataNascimento(resultSet.getDate("data_nascimento"));
            autor.setBiografia(resultSet.getString("breve_biografica"));

            municipio.setCodigo(resultSet.getLong("m.id_municipio"));
            municipio.setNome(resultSet.getString("nome_municipio"));
            autor.setMunicipio(municipio);
        }
        return autor;

    }

    
    public List<Autor> listaAutores() {
        List<Autor> autores = new ArrayList<>();
        try {
            PreparedStatement ps = conexao.prepareStatement(BUSCAR_TUDO);
          
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
               
                Autor autor = new Autor();
               
              popularAutor(autor, rs);
                 
                autores.add(autor);
            }
        } catch (SQLException ex) {
            System.out.println("Erro a listar do dados dos autores" + ex);
        }
        
        return autores;
    }
    @Override
    public List<Autor> buscarTudo() {
        List<Autor> autores = new ArrayList<>();
        try {
            PreparedStatement ps = conexao.prepareStatement(BUSCAR_TUDO);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Autor autor = new Autor();
               // autor.setCodigo(rs.getLong(1));
                //autor.setNome(rs.getString(2));
              popularAutor(autor, rs);
                autores.add(autor);
            }
        } catch (SQLException ex) {
            System.out.println("Erro a listar do dados dos autores" + ex);
        }
        return autores;
    }

    public List<Autor> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    private List<Autor> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
 ResultSet resultSet;
        String sql = "SELECT a.* FROM autor a";
        if (filtroActivo) {
            sql = sql + " LIMIT ?, ?";
        }

        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        if (filtroActivo) {
            prepareStatement.setLong(1, linhaInicial);
            prepareStatement.setLong(2, totalDeLinhas);
        }

        resultSet = prepareStatement.executeQuery();

        List<Autor> autores = new ArrayList<>();
        while (resultSet.next()) {
            Autor autor = new Autor();
            autor.setCodigo(resultSet.getLong("id_autor"));
            autor.setNome(resultSet.getString("nome_autor"));
            autor.setSobrenome(resultSet.getString("sobrenome_autor"));
            autor.setDataNascimento(resultSet.getDate("data_nascimento"));
            autor.setBiografia(resultSet.getString("breve_biografica"));
            autores.add(autor);
        }
        resultSet.close();
        return autores;

    }

    private void popularAutor(Autor autor, ResultSet resultSet) {
        try {
            autor.setCodigo(resultSet.getLong("a.id_autor"));
            autor.setNome(resultSet.getString("a.nome_autor"));
            autor.setSobrenome(resultSet.getString("a.sobrenome_autor"));
            autor.setDataNascimento(resultSet.getDate("a.data_nascimento"));
            autor.setBiografia(resultSet.getString("a.breve_biografica"));

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao ler dados " + ex.getMessage());
        }
    }

}
