/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpj.biblioteca.dao;

import com.cpj.biblioteca.conexao.Conexao;
import com.cpj.biblioteca.modelo.Editora;
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
 * @author toshiba
 */
public class EditoraDAO implements DAO<Editora>{
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    @Override
    public boolean salvar(Editora editora) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "INSERT INTO editora(nome_editora, rua_editora, casa_editora, id_municipio) VALUES (?, ?, ?, ?)";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, editora.getNome());
            prepareStatement.setString(2, editora.getRua());
            prepareStatement.setString(3, editora.getCasa());
            prepareStatement.setLong(4, editora.getMunicipio().getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean editar(Editora editora) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "UPDATE editora SET nome_editora = ?, rua_editora = ?, casa_editora = ?, id_municipio = ? "
                    + "WHERE id_editora = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setString(1, editora.getNome());
            prepareStatement.setString(2, editora.getRua());
            prepareStatement.setString(3, editora.getCasa());
            prepareStatement.setLong(4, editora.getMunicipio().getCodigo());
            prepareStatement.setLong(5, editora.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public boolean excluir(Editora editora) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "DELETE FROM editora WHERE id_editora = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, editora.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } finally {
            Conexao.fecharTudo(prepareStatement);
        }
    }

    @Override
    public Editora buscarPeloCodigo(Serializable codigo) throws ClassNotFoundException, SQLException {
        try {
            
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT e.*, m.* FROM editora e, municipio m where e.id_municipio = m.id_municipio AND e.id_editora = ?";
            prepareStatement = conexao.prepareStatement(sql);
            prepareStatement.setLong(1, (Long)codigo);
            resultSet = prepareStatement.executeQuery();
            Editora editora=  new Editora();
            if(resultSet.next()) {
                Municipio municipio = new Municipio();
                editora.setCodigo(resultSet.getLong("id_editora"));
                editora.setNome(resultSet.getString("nome_editora"));
                editora.setRua(resultSet.getString("rua_editora"));
                editora.setCasa(resultSet.getString("casa_editora"));
                
                municipio.setCodigo(resultSet.getLong("m.id_municipio"));
                municipio.setNome(resultSet.getString("nome_municipio"));
                editora.setMunicipio(municipio);
            }
            return editora;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }

    @Override
    public List<Editora> buscarTudo() throws ClassNotFoundException, SQLException {
        return aplicarFiltro(0L, 0L, false);
    }
    
    public List<Editora> filtrar(Long linhaInicial, Long totalDeLinhas) throws ClassNotFoundException, SQLException {
        return aplicarFiltro(linhaInicial, totalDeLinhas, true);
    }
    
    private List<Editora> aplicarFiltro(Long linhaInicial, Long totalDeLinhas, boolean filtroActivo) throws ClassNotFoundException, SQLException {
        try {
            Connection conexao = Conexao.criarConexao();
            String sql = "SELECT e.*, m.* FROM editora e, municipio m where e.id_municipio = m.id_municipio";
            if(filtroActivo) {
                sql = sql + " LIMIT ?, ?";
            }
            
            prepareStatement = conexao.prepareStatement(sql);
            if(filtroActivo) {
                prepareStatement.setLong(1, linhaInicial);
                prepareStatement.setLong(2, totalDeLinhas);
            }
            
            resultSet = prepareStatement.executeQuery();
            
            List<Editora> editoras = new ArrayList<>();
            if(resultSet.next()) {
                Editora editora=  new Editora();
                Municipio municipio = new Municipio();
                editora.setCodigo(resultSet.getLong("id_editora"));
                editora.setNome(resultSet.getString("nome_editora"));
                editora.setRua(resultSet.getString("rua_editora"));
                editora.setCasa(resultSet.getString("casa_editora"));
                
                municipio.setCodigo(resultSet.getLong("m.id_municipio"));
                municipio.setNome(resultSet.getString("nome_municipio"));
                editora.setMunicipio(municipio);
                editoras.add(editora);
            }
            return editoras;
        } finally {
            Conexao.fecharTudo(prepareStatement, resultSet);
        }
    }
    
    
}
