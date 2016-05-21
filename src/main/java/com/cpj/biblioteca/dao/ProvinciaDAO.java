package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Provincia;
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
public class ProvinciaDAO implements DAO<Provincia>{
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;
    
    @Override
    public boolean salvar(Provincia provincia) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO provincia(nome_provincia) VALUES (?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, provincia.getNome());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Provincia provincia) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE provincia SET nome_provincia = ? WHERE id_provincia = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, provincia.getNome());
            prepareStatement.setLong(2, provincia.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Provincia provincia) throws ClassNotFoundException, SQLException {
        return excluir(provincia.getCodigo());
    }
 
    @Override
    public boolean excluir(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM provincia WHERE id_provincia = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Provincia buscarPeloCodigo(Serializable codigo) throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM provincia WHERE id_provincia = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            
            resultSet = prepareStatement.executeQuery();
            Provincia provincia = new Provincia();
            
            if (resultSet.next()) {                
                provincia.setCodigo(resultSet.getLong("id_provincia"));
                provincia.setNome(resultSet.getString("nome_provincia"));
            }
            
            return provincia;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Provincia> buscarTudo() throws SQLException, ClassNotFoundException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT * FROM provincia";
            prepareStatement = conexao.prepareStatement(sql);
            
            resultSet = prepareStatement.executeQuery();
            List<Provincia> provincias = new ArrayList<>();
            
            while (resultSet.next()) { 
                Provincia provincia = new Provincia();
                provincia.setCodigo(resultSet.getLong("id_provincia"));
                provincia.setNome(resultSet.getString("nome_provincia"));
                provincias.add(provincia);
            }
            
            return provincias;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
}
