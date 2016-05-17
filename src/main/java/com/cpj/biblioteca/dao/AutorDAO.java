/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author toshiba
 */
public class AutorDAO {

    private ResultSet resultSet;
    Connection conexao;

    public AutorDAO() {
        conexao = Conexao.criarConexao();
    }

    public boolean salvar(Autor autor) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO autor(nome_autor, sobrenome_autor, data_nascimento, breve_biografica, id_municipio) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setString(1, autor.getNome());
        prepareStatement.setString(2, autor.getSobrenome());
        prepareStatement.setDate(3, new Date(autor.getDataNascimento().getTime()));
        prepareStatement.setString(4, autor.getBiografia());
        prepareStatement.setLong(5, autor.getMunicipio().getCodigo());
        prepareStatement.close();
        return prepareStatement.executeUpdate() > 0;
    }

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

    public boolean excluir(Autor autor) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM autor WHERE id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, autor.getCodigo());
        return prepareStatement.executeUpdate() > 0;

    }

    public Autor buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {

        Connection conexao = Conexao.criarConexao();
        String sql = "SELECT a.*, m.* FROM autor a, municipio m WHERE a.id_municipio = m.id_municipio AND id_autor = ?";
        PreparedStatement prepareStatement = conexao.prepareStatement(sql);
        prepareStatement.setLong(1, (Long) codigo);
        resultSet = prepareStatement.executeQuery();
        Autor autor = new Autor();
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

    public List<Autor> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }

    public List<Autor> filtar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }

    public List<Autor> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
      
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

}
