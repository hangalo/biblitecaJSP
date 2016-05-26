package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Lingua;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chandimba
 */
public class LinguaDAO implements DAO<Lingua> {

    private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    
    @Override
    public boolean salvar(Lingua lingua) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO lingua(nome_lingua) VALUES (?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, lingua.getNome());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Lingua lingua) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE lingua SET nome_lingua = ? WHERE id_lingua = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, lingua.getNome());
            prepareStatement.setLong(2, lingua.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Lingua lingua) throws ClassNotFoundException, SQLException {
        return excluir(lingua.getCodigo());
    }

    @Override
    public boolean excluir(Serializable lingua) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM lingua WHERE id_lingua = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)lingua);
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Lingua buscarPeloCodigo(Lingua lingua) throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM lingua WHERE id_lingua = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, lingua.getCodigo());
            
            resultSet = prepareStatement.executeQuery();
                      
            if (resultSet.next()) {                
                lingua.setCodigo(resultSet.getLong("id_lingua"));
                lingua.setNome(resultSet.getString("nome_lingua"));
            }
            
            return lingua;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Lingua> buscarTudo() throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM lingua";
            prepareStatement = conexao.prepareStatement(sql);
            
            resultSet = prepareStatement.executeQuery();
            List<Lingua> linguas = new ArrayList<>();
            
            while (resultSet.next()) { 
                Lingua lingua = new Lingua();
                lingua.setCodigo(resultSet.getLong("id_lingua"));
                lingua.setNome(resultSet.getString("nome_lingua"));
                linguas.add(lingua);
            }
            
            return linguas;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
