/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Municipio;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandimba
 */
public class MunicipioDAO implements DAO<Municipio>{
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    
    @Override
    public boolean salvar(Municipio municipio) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO municipio(nome_municipio, id_provincia) VALUES (?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, municipio.getNome());
            prepareStatement.setLong(2, municipio.getProvincia().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Municipio municipio) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE municipio SET nome_municipio = ?, id_provincia = ? WHERE id_municipio = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, municipio.getNome());
            prepareStatement.setLong(2, municipio.getProvincia().getCodigo());
            prepareStatement.setLong(3, municipio.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Municipio municipio) throws ClassNotFoundException, SQLException {
        return excluir(municipio.getCodigo());
    }

    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM municipio WHERE id_municipio = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Municipio buscarPeloCodigo(Serializable codigo) throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM municipio WHERE id_municipio = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            
            resultSet = prepareStatement.executeQuery();
            Municipio municipio = new Municipio();
            
            if (resultSet.next()) {                
                municipio.setCodigo(resultSet.getLong("id_municipio"));
                municipio.setNome(resultSet.getString("nome_municipio"));
            }
            
            return municipio;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Municipio> buscarTudo() throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM municipio";
            prepareStatement = conexao.prepareStatement(sql);
            
            resultSet = prepareStatement.executeQuery();
            List<Municipio> provincias = new ArrayList<>();
            
            while (resultSet.next()) { 
                Municipio municipio = new Municipio();
                municipio.setCodigo(resultSet.getLong("id_municipio"));
                municipio.setNome(resultSet.getString("nome_municipio"));
                provincias.add(municipio);
            }
            
            return provincias;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
