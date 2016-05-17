/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Categoria;
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
public class CategoriaDAO implements DAO<Categoria>{
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    
    @Override
    public boolean salvar(Categoria categoria) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO categoria(nome_categoria) VALUES (?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, categoria.getNome());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Categoria categoria) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE categoria SET nome_categoria = ? WHERE id_categoria = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, categoria.getNome());
            prepareStatement.setLong(2, categoria.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Categoria categoria) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM categoria WHERE id_categoria = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, categoria.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Categoria buscarPeloCodigo(Serializable codigo) throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            
            resultSet = prepareStatement.executeQuery();
            Categoria categoria = new Categoria();
            
            if (resultSet.next()) {                
                categoria.setCodigo(resultSet.getLong("id_categoria"));
                categoria.setNome(resultSet.getString("nome_categoria"));
            }
            
            return categoria;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Categoria> buscarTudo() throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM categoria";
            prepareStatement = conexao.prepareStatement(sql);
            
            resultSet = prepareStatement.executeQuery();
            List<Categoria> categorias = new ArrayList<>();
            
            while (resultSet.next()) { 
                Categoria categoria = new Categoria();
                categoria.setCodigo(resultSet.getLong("id_categoria"));
                categoria.setNome(resultSet.getString("nome_categoria"));
                categorias.add(categoria);
            }
            
            return categorias;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
